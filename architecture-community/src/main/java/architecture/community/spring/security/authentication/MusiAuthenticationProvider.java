package architecture.community.spring.security.authentication;

import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import architecture.community.spring.security.userdetails.MusiUserDetails;
import architecture.community.user.UserManager;
import architecture.community.user.UserTemplate;

public class MusiAuthenticationProvider extends DaoAuthenticationProvider {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	@Qualifier("userManager")
	private UserManager userManager;	
	

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

		logger.debug("=========== credentials  = {}" , authentication.getCredentials());
		
		if (authentication.getCredentials() == null)
		    throw new BadCredentialsException("Bad credentials");
		
		super.additionalAuthenticationChecks(userDetails, authentication);

		try {
			// do something for login success ...
			
			MusiUserDetails user = (MusiUserDetails) userDetails;
			try {
			    if ( user.getUser() != null) {
			    	UserTemplate template = new UserTemplate(user.getUser());
			    	//template.setLastLoggedIn(new Date());
			    	//userManager.updateUser(template);
			    }
			} catch (Exception e) {
			    //log.warn(L10NUtils.format("005016", user), e);
			}
		} catch (Exception e) {
		    logger.error("Unable to coerce user detail to MusiUserDetails.");
		    throw new BadCredentialsException( messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}		
	}
}