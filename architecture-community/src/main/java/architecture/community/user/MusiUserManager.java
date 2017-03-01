package architecture.community.user;

import javax.inject.Inject;

import org.ehcache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;

import architecture.community.i18n.CommunityLogLocalizer;
import architecture.community.user.dao.UserDao;
import architecture.ee.util.StringUtils;

public class MusiUserManager implements UserManager {

	
	private static final Logger log = LoggerFactory.getLogger(MusiUserManager.class);
	
	@Inject
	@Qualifier("userDao")
	private UserDao userDao;
	
	@Inject
	@Qualifier("passwordEncoder")
	protected PasswordEncoder passwordEncoder;
	
	@Inject
	@Qualifier("userCache")
    private Cache<Long, User> userCache;
    
	@Inject
	@Qualifier("userIdCache")
    private Cache<String, Long> userIdCache;
    
	
	public User getUser(User template) {
		return getUser(template, true);
	}

	public User getUser(User template, boolean caseSensitive) {
		User user = null;
		
		if( template.getUserId() == -1L) {
			return UserTemplate.ANONYMOUS;
		}
		
		if( template.getUserId() > 0L ){
			user = getUserInCache( template.getUserId() );
			if( user == null)
			{				
				try {
					user = userDao.getUserById(template.getUserId());
					updateCaches(user);
				} catch (Throwable e) {
					log.error(CommunityLogLocalizer.getMessage("010005"), e);
				}		
				
			}
		}
		
		if( user == null && StringUtils.isNullOrEmpty(template.getUsername()) )
		{
			String nameToUse = template.getUsername();
			long userIdToUse = getUserIdInCache(nameToUse);			
			if( userIdToUse > 0L ){
				user = getUserInCache( template.getUserId() );		
				if( user == null )
				{					
					if (!caseSensitive) {
						nameToUse = nameToUse.toUpperCase();	
					}					
					try {
						user = userDao.getUserByUsername(nameToUse);
						updateCaches(user);
					} catch (Throwable e) {
						log.error(CommunityLogLocalizer.getMessage("010004"), e);
					}
				}
			}
		}		
		
		if (null == user && StringUtils.isNullOrEmpty(template.getEmail()) ) {
		    try {
		    	user = userDao.getUserByEmail(template.getEmail());
		    	updateCaches(user);
		    }
		    catch (Exception ex) {
		    	log.debug(CommunityLogLocalizer.getMessage("010006"), ex);
		    }
		}		
		return user;
	}

	
	public User getUser(String username) throws UserNotFoundException {
		User user = getUser(((User) (new UserTemplate(username))));
		if (null == user) {
		    UserNotFoundException e = new UserNotFoundException(CommunityLogLocalizer.format("010002", username));
		    throw e;
		}
		return user;
	}
	
	public User getUser(long userId) throws UserNotFoundException {
		User user = getUser(((User) (new UserTemplate(userId))));
		if (null == user) {
		    UserNotFoundException e = new UserNotFoundException(CommunityLogLocalizer.format("010002", userId));
		    throw e;
		}
		return user;
	}
	
	
	protected long getUserIdInCache(String username){
		if( userIdCache.get(username) != null ){
			return userIdCache.get(username);
		}		
		return -2L;
	}
	
	protected void updateCaches(User user){
		if(user != null)
		{
			if ( user.getUserId() > 0 && StringUtils.isNullOrEmpty(user.getUsername() )){
				userIdCache.remove(user.getUsername());
				userIdCache.put(user.getUsername(), user.getUserId());
				userCache.remove(user.getUserId());
				userCache.put(user.getUserId(), user);
			}
		}
	}
	
	protected User getUserInCache(Long userId){		
		if( userCache.get(userId) != null )
			return userCache.get(userId);			
		return null;
	}

	
	private String getPasswordHash(User user) {
		String passwd;
		passwd = user.getPassword();
		if( StringUtils.isNullOrEmpty(passwd))
			return null;		
		try {	
			return passwordEncoder.encode(passwd);
		} catch (Exception ex) {			
			log.warn(CommunityLogLocalizer.getMessage("010001"), ex);
		}		
		return null;
	}
}
