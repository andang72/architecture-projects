package architecture.community.spring.security.userdetails;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author donghyuck
 *
 */
public class MusiUserDetails extends User {

	@JsonIgnore
	private final architecture.community.user.User communityUser;

	public MusiUserDetails(architecture.community.user.User communityUser) {
		super(communityUser.getUsername(), communityUser.getPassword(), communityUser.isEnabled(), true, true, true, Collections.EMPTY_LIST);
		this.communityUser = communityUser;
	}

	public MusiUserDetails(architecture.community.user.User communityUser, List<GrantedAuthority> authorities) {
		super(communityUser.getUsername(), communityUser.getPassword(), communityUser.isEnabled(), true, true, true,
				authorities);
		this.communityUser = communityUser;
	}

	public boolean isAnonymous() {
		return communityUser.isAnonymous();
	}

	public architecture.community.user.User getUser() {
		return communityUser;
	}

	public long getUserId() {
		return communityUser.getUserId();
	}

	public long getCreationDate() {
		return communityUser.getCreationDate() != null ? communityUser.getCreationDate().getTime() : -1L;
	}

}
