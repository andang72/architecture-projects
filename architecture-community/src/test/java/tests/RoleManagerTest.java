package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import architecture.community.user.Role;
import architecture.community.user.RoleAlreadyExistsException;
import architecture.community.user.RoleManager;
import architecture.community.user.RoleNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("WebContent/")
@ContextConfiguration(locations = { "classpath:application-user-context.xml" })
public class RoleManagerTest {

	@Autowired
	private RoleManager roleManager;

	private static Logger log = LoggerFactory.getLogger(RoleManagerTest.class);

	
	@Test
	public void testCreateRoleIfNotExist() {
		log.debug("=================== > ROLE ");
		try {
			Role role = roleManager.getRole("ROLE_USER");	
		} catch (RoleNotFoundException e) {		
			try {
				roleManager.createRole("ROLE_USER");
			} catch (RoleAlreadyExistsException e1) {
				e1.printStackTrace();
			}			
		}	
	}
	
}
