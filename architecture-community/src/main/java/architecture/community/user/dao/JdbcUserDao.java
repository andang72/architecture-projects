package architecture.community.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.stereotype.Repository;

import architecture.community.i18n.CommunityLogLocalizer;
import architecture.community.user.User;
import architecture.community.user.UserTemplate;
import architecture.ee.spring.jdbc.ExtendedJdbcDaoSupport;
import architecture.ee.util.StringUtils;

@Repository("userDao")
public class JdbcUserDao extends ExtendedJdbcDaoSupport implements UserDao {
	
	//private static final Logger log = LoggerFactory.getLogger(JdbcUserDao.class);
	
	private final RowMapper<UserTemplate> userMapper = new RowMapper<UserTemplate>() {

		public UserTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserTemplate ut = new UserTemplate();
			ut.setUserId(rs.getLong("USER_ID"));
			ut.setUsername(rs.getString("USERNAME"));
			ut.setPasswordHash(rs.getString("PASSWORD_HASH"));
			ut.setName(rs.getString("NAME"));
			ut.setNameVisible(rs.getInt("NAME_VISIBLE") == 1);
			ut.setFirstName(rs.getString("FIRST_NAME"));
			ut.setLastName(rs.getString("LAST_NAME"));
			ut.setEmail(rs.getString("EMAIL"));
			ut.setEmailVisible(rs.getInt("EMAIL_VISIBLE") == 1);
			ut.setEnabled(rs.getInt("USER_ENABLED") == 1);
			ut.setStatus(UserTemplate.Status.getById(rs.getInt("STATUS")));
			ut.setCreationDate(rs.getDate("CREATION_DATE"));
			ut.setModifiedDate(rs.getDate("MODIFIED_DATE"));
			return ut;
		}
	};

	public JdbcUserDao() {
		super();
	}

	public User getUserById(long userId) {		
		if (userId <= 0L) {
		    return null;
		}		
		UserTemplate user = null;
		try {
			user = getExtendedJdbcTemplate().queryForObject(getBoundSql("COMMUNITY.SELECT_USER_BY_ID").getSql(), 
					userMapper, 
					new SqlParameterValue(Types.NUMERIC, userId));
		} catch (IncorrectResultSizeDataAccessException e) {
		    if (e.getActualSize() > 1) {
		    	logger.warn(CommunityLogLocalizer.format("010008", userId));
				throw e;
		    }
		} catch (DataAccessException e) {
			logger.error(CommunityLogLocalizer.format("010007", userId), e);
		}
		return user;
	}

	public User getUserByUsername(String username) {
		if(StringUtils.isNullOrEmpty(username))
			return null;
		
		UserTemplate user = null;		
		try {
			user = getExtendedJdbcTemplate().queryForObject(getBoundSql("COMMUNITY.SELECT_USER_BY_USERNAME").getSql(), 
					userMapper, 
					new SqlParameterValue(Types.VARCHAR, username));
		} catch (EmptyResultDataAccessException e) {
			logger.error(CommunityLogLocalizer.format("010009", username), e);
		} catch (DataAccessException e) {
			logger.error(CommunityLogLocalizer.getMessage("010004"), e);
		}
		return user;
	}

	public User getUserByNameOrEmail(String nameOrEmail) {
		
		if(StringUtils.isNullOrEmpty(nameOrEmail))
			return null;
		
		UserTemplate usertemplate = null;
		
		return getExtendedJdbcTemplate().queryForObject(getBoundSql("COMMUNITY.SELECT_USER_BY_NAME_OR_EMAIL").getSql(), 
				userMapper, 
				new SqlParameterValue(Types.VARCHAR, nameOrEmail));
	}

}
