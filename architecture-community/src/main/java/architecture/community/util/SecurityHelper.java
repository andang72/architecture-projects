package architecture.community.util;

import java.util.Collection;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import architecture.community.spring.security.userdetails.CommuintyUserDetails;
import architecture.community.spring.security.userdetails.CommunityUserDetailsService;
import architecture.community.user.User;
import architecture.community.user.UserTemplate;
import architecture.ee.util.StringUtils;

public class SecurityHelper {

	public static final User ANONYMOUS = new UserTemplate(-1L, "ANONYMOUS");
	
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static User getUser() {
		try {
		    Authentication authen = getAuthentication();
		    Object obj = authen.getPrincipal();
		    if (obj instanceof CommuintyUserDetails)
			return ((CommuintyUserDetails) obj).getUser();
		} catch (Exception ignore) {
			
		}
		return ANONYMOUS;
	}
	
	private static boolean isGranted(String role) {
		if( StringUtils.isEmpty(role)){
			return true;
		}
		
		Authentication auth = getAuthentication();
		if ((auth == null) || (auth.getPrincipal() == null)) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		if (authorities == null) {
			return false;
		}
		for (GrantedAuthority grantedAuthority : authorities) {
			if (role.equals(grantedAuthority.getAuthority())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 인자로 전달된 role 을 현재 사용자가 가지고 있는지 여부를 확인.
	 * 
	 * @param roles
	 * @return
	 */
	public static boolean isUserInRole(String roles) {
		
		boolean flag = false;
		boolean returnFlag = false;
		
		if( StringUtils.isEmpty(roles)){
			returnFlag = true;
		}
		
		if( !StringUtils.isNullOrEmpty(roles)){
			for(String token : StringUtils.tokenizeToStringArray(roles, ",")){
				flag = isGranted(token);
				if(flag == true){
					returnFlag = true;
				}
			}
		}
		return returnFlag;
	}

	
	/**
	 * 익명 사용자 여부를 확인 
	 * 
	 * @return
	 */
	public static boolean isAnonymous(){
		if( getAuthentication()!=null && getAuthentication().isAuthenticated() && ! (getAuthentication() instanceof AnonymousAuthenticationToken) )
			return false;
		else 
			return true;
	}
	
	public static void refreshUserToken(){
		
		User user = getUser();
		
		if( !user.isAnonymous() ){
			CommunityUserDetailsService detailsService = ApplicationHelper.getComponent(CommunityUserDetailsService.class);
			UserDetails details = detailsService.loadUserByUsername(user.getUsername());
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( details, null , details.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(token);
		}
		
	}
	
}
