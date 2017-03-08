package architecture.community.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.stereotype.Repository;

import architecture.community.i18n.CommunityLogLocalizer;
import architecture.community.user.User;
import architecture.community.user.UserTemplate;
import architecture.ee.jdbc.sequencer.SequencerFactory;
import architecture.ee.jdbc.sequencer.annotation.MaxValue;
import architecture.ee.service.ConfigService;
import architecture.ee.spring.jdbc.ExtendedJdbcDaoSupport;
import architecture.ee.util.StringUtils;

@Repository("userDao")
@MaxValue("USER")
public class JdbcUserDao extends ExtendedJdbcDaoSupport implements UserDao {

	@Inject
	@Qualifier("configService")
	private ConfigService configService;
	
	@Inject
	@Qualifier("sequencerFactory")
	private SequencerFactory sequencerFactory;

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
	
	public long getNextUserId(){
		return sequencerFactory.getNextValue(this);
	}

	public User getUserById(long userId) {
		if (userId <= 0L) {
			return null;
		}
		UserTemplate user = null;
		try {
			user = getExtendedJdbcTemplate().queryForObject(getBoundSql("COMMUNITY_USER.SELECT_USER_BY_ID").getSql(),
					userMapper, new SqlParameterValue(Types.NUMERIC, userId));
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

	public User getUserByEmail(String email) {
		if (StringUtils.isNullOrEmpty(email))
			return null;
		String emailMatch = email.replace('*', '%');
		UserTemplate usertemplate = null;
		try {
			UserTemplate user = getExtendedJdbcTemplate().queryForObject(
					getBoundSql("COMMUNITY_USER.SELECT_USER_BY_EMAIL").getSql(), userMapper,
					new SqlParameterValue(Types.VARCHAR, emailMatch));

		} catch (IncorrectResultSizeDataAccessException e) {
			if (e.getActualSize() > 1) {
				logger.warn(CommunityLogLocalizer.format("010010", email));
				throw e;
			}
		} catch (DataAccessException e) {
			logger.error(CommunityLogLocalizer.format("010011", emailMatch), e);
			throw e;
		}
		return usertemplate;
	}

	private String formatUsername(String username) {
		if (StringUtils.isNullOrEmpty(username))
			return null;
		boolean allowWhiteSpace = false;
		try {
			allowWhiteSpace = configService.getApplicationBooleanProperty("username.allowWhiteSpace", false);
		} catch (Throwable ignore) {}
		if (allowWhiteSpace) {
			String formattedUsername = "";
			Pattern p = Pattern.compile("\\s+");
			Matcher m = p.matcher(username.trim());
			if (m.find()) {
				formattedUsername = m.replaceAll(" ");
				username = formattedUsername;
			}
		} else {
			username = StringUtils.trimAllWhitespace(username);
		}
		return username;
	}

	public User createUser(User user) {
		UserTemplate template = new UserTemplate(user);
		if (template.getEmail() == null)
			throw new IllegalArgumentException(CommunityLogLocalizer.getMessage("010012"));		
		
		
		long nextUserId = getNextUserId() ;
				
		if ("".equals(template.getName()))
			template.setName(null);
		
		template.setEmail(template.getEmail().toLowerCase());
		if (template.getStatus() == null || template.getStatus() == User.Status.NONE)
			template.setStatus(User.Status.REGISTERED);
		
		boolean useLastNameFirstName = !StringUtils.isNullOrEmpty(template.getFirstName()) && !StringUtils.isNullOrEmpty(template.getLastName());
		
		try {
			Date now = new Date();
			getExtendedJdbcTemplate().update(getBoundSql("COMMUNITY_USER.CREATE_USER").getSql(),
					new SqlParameterValue(Types.NUMERIC, nextUserId),
					new SqlParameterValue(Types.VARCHAR, template.getUsername()),
					new SqlParameterValue(Types.VARCHAR, template.getPasswordHash()),
					new SqlParameterValue(Types.VARCHAR, useLastNameFirstName ? template : user.getName()),
					new SqlParameterValue(Types.NUMERIC, template.isNameVisible() ? 1 : 0 ),
					new SqlParameterValue(Types.VARCHAR, useLastNameFirstName ? template.getFirstName() : null),
					new SqlParameterValue(Types.VARCHAR, useLastNameFirstName ? template.getLastName() : null),
					new SqlParameterValue(Types.VARCHAR, template.getEmail()),
					new SqlParameterValue(Types.NUMERIC, template.isEmailVisible() ? 1 : 0 ),
					new SqlParameterValue(Types.NUMERIC, template.isEnabled() ? 1 : 0 ),
					new SqlParameterValue(Types.NUMERIC, template.getStatus().getId() ),
					new SqlParameterValue(Types.TIMESTAMP, template.getCreationDate() != null ? template.getCreationDate() : now ),
					new SqlParameterValue(Types.TIMESTAMP, template.getModifiedDate() != null ? template.getModifiedDate() : now )
			);
		} catch (DataAccessException e) {
			logger.error(CommunityLogLocalizer.getMessage("010013"), e);
			throw e;
		}
		return template;
	}

	public User getUserByUsername(String username) {
		if (StringUtils.isNullOrEmpty(username))
			return null;
		UserTemplate user = null;
		try {
			user = getExtendedJdbcTemplate().queryForObject(getBoundSql("COMMUNITY_USER.SELECT_USER_BY_USERNAME").getSql(),
					userMapper, new SqlParameterValue(Types.VARCHAR, username));
		} catch (EmptyResultDataAccessException e) {
			logger.error(CommunityLogLocalizer.format("010009", username), e);
		} catch (DataAccessException e) {
			logger.error(CommunityLogLocalizer.getMessage("010004"), e);
		}
		return user;
	}

	public void deleteUser(User user) {
		getExtendedJdbcTemplate().update(getBoundSql("COMMUNITY_USER.DELETE_USER_BY_ID").getSql(), new SqlParameterValue(Types.NUMERIC, user.getUserId()));		
	}

}
