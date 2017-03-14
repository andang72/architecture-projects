package tests;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.StringUtils;

import architecture.community.user.Role;
import architecture.community.user.RoleAlreadyExistsException;
import architecture.community.user.RoleManager;
import architecture.community.user.RoleNotFoundException;
import architecture.community.user.User;
import architecture.community.user.UserManager;
import architecture.community.user.UserNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("WebContent/")
@ContextConfiguration(locations = { "classpath:application-user-context.xml" })
public class RoleManagerTest {

	@Autowired
	private RoleManager roleManager;

	@Autowired
	private UserManager userManager;
	
	private static Logger log = LoggerFactory.getLogger(RoleManagerTest.class);

	@Test
	public void testRoleIn(){
		String roles = "A, B, C";
		for( String str :  StringUtils.tokenizeToStringArray(roles, ",")){
			log.debug("------------>" +str);
		}
		
		roles = "A";
		for( String str :  StringUtils.tokenizeToStringArray(roles, ",")){
			log.debug("------------>>" +str);
		}
	}
	@Test
	public void testAllExistRoles() {
		
		for( Role role : roleManager.getRoles() ){
			log.debug("ID:{}, NAME:{}" , role.getRoleId(), role.getName() );	
		}
	}
	
	@Test
	public void testCreateRoleIfNotExist() {		
		boolean doCreate = false;
		try {
			Role role = roleManager.getRole("ROLE_USER");	
			log.debug("ROLE : " + role );
		} catch (RoleNotFoundException e) {	
			doCreate = true;		
		}	
		if(doCreate){
			try {
				roleManager.createRole("ROLE_USER");
			} catch (RoleAlreadyExistsException e1) {
			}	
		}
		doCreate = false;
		try {
			Role role = roleManager.getRole("ROLE_ADMINISTRATOR");	
			log.debug("ROLE : " + role );
		} catch (RoleNotFoundException e) {	
			doCreate = true;		
		}	
		if(doCreate){
			try {
				roleManager.createRole("ROLE_ADMINISTRATOR");
			} catch (RoleAlreadyExistsException e1) {
			}	
		}
	}
	
	@Test
	public void testUserRole(){
		try {
			
			User user = userManager.getUser("king");
			log.debug("######## USER : " + user.getUserId() );
			List<Role> roles = roleManager.getUserRoles(user);
			boolean isAdmin = false;
			for(Role role : roles ){
				log.debug("######## ROLE : " + role.getName() );
				if( "ROLE_ADMINISTRATOR".equals( role.getName() ) ){
					isAdmin = true;
				}
			}
			
			for(Role role : roleManager.getFinalUserRoles(user.getUserId()) ){
				log.debug("######## FINAL ROLE : " + role.getName() );				
			}
			
			if(!isAdmin ){
				try {
					Role role = roleManager.getRole("ROLE_ADMINISTRATOR");
					
					roleManager.grantRole(role, user);
					
				} catch (RoleNotFoundException e) {					
					e.printStackTrace();
				}	
				
			}
			
			log.debug("{} : {}", user.getName(), roleManager.getUserRoles(user));
		} catch (UserNotFoundException e) {
		}
		
	}
	
}
