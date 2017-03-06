package architecture.community.spring.security.userdetails;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import architecture.community.user.User;
import architecture.community.user.UserManager;
import architecture.community.user.UserNotFoundException;
import architecture.community.web.util.CommunityConstants;
import architecture.ee.service.ConfigService;
import architecture.ee.spring.event.EventSupport;
import architecture.ee.util.StringUtils;
public class MusiUserDetailsService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	@Qualifier("userManager")
	private UserManager userManager;	
	
	@Inject
	@Qualifier("configService")
	private ConfigService configService;	
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {			
			User user = userManager.getUser(username);
			MusiUserDetails details = new MusiUserDetails(user, getFinalUserAuthority(user));
			return details ;			
		} catch (UserNotFoundException e) {
			throw new UsernameNotFoundException("User not found.", e);	
		}
	}

	protected List<GrantedAuthority> getFinalUserAuthority(User user) {		
		
		String authority = configService.getLocalProperty(CommunityConstants.SECURITY_AUTHENTICATION_AUTHORITY_PROP_NAME);
		List<String> roles = new ArrayList<String>();		
		if( StringUtils.isNullOrEmpty( authority ))
		{
			authority = authority.trim();
		    if (!roles.contains(authority)) {
			roles.add(authority);
		    }
		}
		return AuthorityUtils.createAuthorityList(StringUtils.toStringArray(roles));
	}
	
}
