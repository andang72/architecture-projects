package architecture.community.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

import architecture.community.user.dao.RoleDao;

public class CommunityRoleManager implements RoleManager {

	private Logger logger = LoggerFactory.getLogger(getClass().getName());

	private com.google.common.cache.LoadingCache<Long, Role> roleCache = null;
	
	private com.google.common.cache.LoadingCache<String, Long> roleIdCache = null;
	
	
	@PostConstruct
	public void initialize(){
		logger.debug("creating cache ...");
		roleCache = CacheBuilder.newBuilder().maximumSize(50).expireAfterAccess(30, TimeUnit.MINUTES).build(		
				new CacheLoader<Long, Role>(){			
					public Role load(Long roleId) throws Exception {
						logger.debug("getRoleById =============");
						return roleDao.getRoleById(roleId);
				}}
			);
		
		roleIdCache = CacheBuilder.newBuilder().maximumSize(50).expireAfterAccess(30, TimeUnit.MINUTES).build(		
				new CacheLoader<String, Long>(){			
					public Long load(String name) throws Exception {	
						logger.debug("getRoleByName ================");
						Role role = roleDao.getRoleByName(name, caseInsensitiveRoleNameMatch);					
						return role.getRoleId();
				}}
		);
	}
	
	@Inject
	@Qualifier("roleDao")
	private RoleDao roleDao;
		
	private boolean caseInsensitiveRoleNameMatch = false;
	
	public Role getRole(String name) throws RoleNotFoundException {
		String nameToUse = caseRoleName(name);
		Long roleId;
		try {
			roleId = roleIdCache.get(nameToUse);
		} catch (Throwable e) {
			throw new RoleNotFoundException(e);
		}		
		return getRole(roleId);
	}

	public Role getRole(long roleId) throws RoleNotFoundException {		
		try {
			return roleCache.get(roleId);
		} catch (ExecutionException e) {
			throw new RoleNotFoundException(e);
		}
	}

	@Override
	public int getTotalRoleCount() {
		return roleDao.getRoleCount();
	}

	@Override
	public List<Role> getRoles() {
		List<Role> roles = new ArrayList<Role>();
		List<Long> roleIds = roleDao.getAllRoleIds();
		for (long roleId : roleIds) {
		    try {
		    	roles.add(getRole(roleId));
		    } catch (RoleNotFoundException e) {
		    }
		}
		return roles;
	}

	@Override
	public Role createRole(String name) throws RoleAlreadyExistsException {
		
		return createRole(name, null);
	}

	@Override
	public Role createRole(String name, String description) throws RoleAlreadyExistsException  {		
		
		try {
			getRole(name);
			throw new RoleAlreadyExistsException();
		} catch (RoleNotFoundException e) {}
		
		Date now = new Date();
		DefaultRole newRole = new DefaultRole();
		newRole.setName(name);
		newRole.setDescription(description);
		newRole.setCreationDate(now);
		newRole.setModifiedDate(now);
		
		roleDao.createRole(newRole);		
		
		return newRole;
		
	}

	@Override
	public void updateRole(Role role) {
		clearCaches(role);
	}

	@Override
	public void deleteRole(Role role) {
		clearCaches(role);
	}

	@Override
	public List<Role> getFinalUserRoles(long userId) {
		return null;
	}

	@Override
	public void grantRole(Role role, User user) {
	}

	@Override
	public void revokeRole(Role role, User user) {
	}

	private String caseRoleName(String name) {
		return caseInsensitiveRoleNameMatch ? name.toUpperCase() : name;
	}

	private void clearCaches(Role role){		
		roleCache.invalidate(role.getRoleId());
		roleIdCache.invalidate(role.getName());
		
	}

}
