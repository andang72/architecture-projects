package architecture.community.spring.security.authentication;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import architecture.community.i18n.CommunityLogLocalizer;
import architecture.community.spring.security.userdetails.CommuintyUserDetails;
import architecture.community.user.UserManager;

public class CommunityAuthenticationProvider extends DaoAuthenticationProvider {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	@Qualifier("userManager")
	private UserManager userManager;	
	

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

		if (authentication.getCredentials() == null)
		    throw new BadCredentialsException(CommunityLogLocalizer.getMessage("010101"));
		
		super.additionalAuthenticationChecks(userDetails, authentication);

		try {
			CommuintyUserDetails user = (CommuintyUserDetails) userDetails;
		} catch (Exception e) {
		    logger.error(CommunityLogLocalizer.getMessage("010102"), e);
		    throw new BadCredentialsException( messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}		
	}
}