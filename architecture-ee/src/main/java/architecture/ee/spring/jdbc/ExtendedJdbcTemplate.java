/**
 *    Copyright 2015-2017 donghyuck
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package architecture.ee.spring.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlProvider;
import org.springframework.util.Assert;

import architecture.ee.spring.jdbc.ExtendedJdbcUtils.DB;

public class ExtendedJdbcTemplate extends JdbcTemplate {

	
	 /**
     * INNER CLASSES
     */
	
	
	private static class ScrollablePreparedStatementCreator implements PreparedStatementCreator, SqlProvider {

		private final String sql;
		
		private final int startIndex;

		private final int numResults;
		
		private DB db ;
		
		public ScrollablePreparedStatementCreator(DB databaseType, String sql, int startIndex, int numResults) {
			Assert.notNull(sql, "SQL must not be null");
		    this.startIndex = startIndex;
		    this.numResults = numResults;
			this.sql = sql;
			this.db = databaseType;
		}
 
		public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			PreparedStatement ps;	
			if (DB.MYSQL == db) {
				StringBuilder builder = new StringBuilder(sql);
				builder.append(" LIMIT ").append(startIndex).append(",").append(numResults);
				ps = connection.prepareStatement(builder.toString());
			} else if (DB.POSTGRESQL == db) {
				StringBuilder builder = new StringBuilder(sql);
				builder.append(" LIMIT ").append(numResults).append(" OFFSET ").append(startIndex);
				ps = connection.prepareStatement(builder.toString());
			} else {
				if (db.scrollResultsSupported) {
					return connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				} else {
					return connection.prepareStatement(sql);
				}
			}
			return ps;
		}
 
		public String getSql() {
			return this.sql;
		}
	}

	public static class ScrollableResultSetExtractor<T> implements ResultSetExtractor<List<T>> {

		private int startIndex;
		private int numResults;
		private RowMapper<T> mapper;
		private DB db ;
		//private Logger log = LoggerFactory.getLogger(getClass());

		public ScrollableResultSetExtractor(DB databaseType, RowMapper<T> mapper, int startIndex, int numResults ) {
			this.startIndex = startIndex;
			this.numResults = numResults;
			this.mapper = mapper;
			this.db = databaseType;
		}
		

		public List<T> extractData(ResultSet rs) throws SQLException {			
			List<T> results =  new ArrayList<T>();
			if (DB.MYSQL == db || DB.POSTGRESQL == db) {
				for (int count = 0; rs.next(); count++)
					results.add(mapper.mapRow(rs, count));
			} else {
				if( db.fetchSizeSupported){
					rs.setFetchSize(startIndex + numResults);
				}
				
				if(db.scrollResultsSupported){
					if (startIndex > 0) {
						rs.setFetchDirection(1000);
						rs.absolute(startIndex);
					}
				}else{
					for (int i = 0; i < startIndex; i++)
						rs.next();
				}
				
				for (int i = 0; i < numResults && rs.next(); i++) {
					T o = mapper.mapRow(rs, i);
					results.add(o);
				}
			}
			return results;
		}
	}

	public ExtendedJdbcTemplate() {
		super();
	}

	
	
	public ExtendedJdbcTemplate(DataSource dataSource) {
		super(dataSource);
	}
	
	// *********************************************
    // Public Methods for Scrollable
    // ********************************************

	public ExtendedJdbcTemplate(DataSource dataSource, boolean lazyInit) {
		super(dataSource, lazyInit);
	}	
	
	public DB getDBInfo(){
		DB db = DB.UNKNOWN;		
		try {
			db = ExtendedJdbcUtils.extractDB(getDataSource().getConnection());
		} catch (SQLException e) {
		}		
		return db;
	}	
	
	public  List<Map<String, Object>> query( String sql, int startIndex, int numResults) throws DataAccessException {			
		return query(
			new ScrollablePreparedStatementCreator(getDBInfo(), sql, startIndex, numResults ),
			null,
			new ScrollableResultSetExtractor<Map<String, Object>>(getDBInfo(), getColumnMapRowMapper(), startIndex, numResults));
	}			
	
	// *********************************************
    // Public Methods for Update and Batch
    // *********************************************

	public  <T> List<T> query( String sql, int startIndex, int numResults, Class<T> elementType, Object... args) throws DataAccessException {			
		return query(
			new ScrollablePreparedStatementCreator(getDBInfo(), sql, startIndex, numResults ), 
			newArgPreparedStatementSetter(args), 
			new ScrollableResultSetExtractor<T>(getDBInfo(), getSingleColumnRowMapper(elementType), startIndex, numResults));
	}
	
	
	public  List<Map<String, Object>> query( String sql, int startIndex, int numResults, Object... args) throws DataAccessException {			
		return query(
			new ScrollablePreparedStatementCreator(getDBInfo(), sql, startIndex, numResults ), 
			newArgPreparedStatementSetter(args), 
			new ScrollableResultSetExtractor<Map<String, Object>>(getDBInfo(), getColumnMapRowMapper(), startIndex, numResults));
	}
	
	
	public  <T> List<T> query( String sql, int startIndex, int numResults, RowMapper<T> rowMapper, Object... args) throws DataAccessException {			
		return query(
			new ScrollablePreparedStatementCreator(getDBInfo(), sql, startIndex, numResults ), 
			newArgPreparedStatementSetter(args), 
			new ScrollableResultSetExtractor<T>(getDBInfo(), rowMapper,startIndex, numResults));
	}



}
