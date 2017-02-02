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
package tests;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import architecture.ee.jdbc.sqlquery.factory.SqlQueryFactory;
import architecture.ee.jdbc.sqlquery.mapping.MappedStatement;
import architecture.ee.spring.jdbc.ExtendedJdbcTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("WebContent/")
@ContextConfiguration(locations={"classpath:application-context3.xml"})
public class SqlQueryTest {

	private static Logger log = LoggerFactory.getLogger(SqlQueryXmlTest.class);
	
	@Autowired
    private SqlQueryFactory sqlQueryFactory;
	
	@Autowired
    private DataSource dataSource;
	
	@Test
	public void testJdbcTemplate1(){		
		ExtendedJdbcTemplate template = new ExtendedJdbcTemplate(dataSource);
		MappedStatement ms = sqlQueryFactory.getConfiguration().getMappedStatement("COMMON.SELECT_TABLE_NAMES");		
		List<String> list = template.queryForList(ms.getBoundSql(null).getSql(), String.class);
		log.debug( list.size() + " >>>>>>>>>> " + list ) ;

	}
	
	@Test
	public void testJdbcTemplate2(){		
		log.debug( ">>>>>>>>>>" ) ;
		ExtendedJdbcTemplate template = new ExtendedJdbcTemplate(dataSource);
		try {
			List list = template.query("select table_name from tabs", 0, 15);
			log.debug( list.size() + " >>>>>>>>>> " + list ) ;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
		
	@Test
	public void testJdbcTemplate3(){				
		ExtendedJdbcTemplate template = new ExtendedJdbcTemplate(dataSource);
		log.debug( template.getDBInfo().toString() ) ;
	}
	
}