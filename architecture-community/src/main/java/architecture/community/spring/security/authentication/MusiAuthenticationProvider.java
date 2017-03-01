package architecture.community.spring.security.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class MusiAuthenticationProvider extends DaoAuthenticationProvider {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

		if (authentication.getCredentials() == null)
		    throw new BadCredentialsException("Bad credentials");
		
		super.additionalAuthenticationChecks(userDetails, authentication);
		// login time update ...
		
		//logger.debug( userDetails.getClass().getName()) ;
		
	}
}