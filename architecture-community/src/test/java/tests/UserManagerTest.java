package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import architecture.community.user.User;
import architecture.community.user.UserManager;
import architecture.community.user.UserNotFoundException;
import architecture.community.user.UserTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("WebContent/")
@ContextConfiguration(locations = { "classpath:application-user-context.xml" })
public class UserManagerTest {

	@Autowired
	private UserManager userManager;

	private static Logger log = LoggerFactory.getLogger(UserManagerTest.class);

	@Test
	public void testUserDaoById() {
		try {
			log.debug("USER" + userManager.getUser(1));
		} catch (UserNotFoundException e) {
			log.error("ERROR" , e);
		}
	}

	@Test
	public void testUserDaoByUsername() {
		try {
			log.debug("USER" + userManager.getUser("test"));
		} catch (UserNotFoundException e) {
			log.error("ERROR" , e);
		}
	}

	@Test
	public void testCreateUserIfNotExist() {
		
		User newUesr = new UserTemplate("king", "1234", "킹", false, "king@king.com", false);
		log.debug("---------------" + newUesr);
		User existUser = userManager.getUser(newUesr);
		log.debug("USER:" + existUser );
		
		if( existUser != null){
			log.debug("now remove : " + existUser );
			try {
				userManager.deleteUser(newUesr);
			} catch (UserNotFoundException e) {
				log.error("ERROR", e );
			}	
		}
		try {
			userManager.createUser(newUesr);
		} catch (Exception e) {
			log.error("ERROR" , e);
		}
		log.debug(newUesr.toString());
	}
	
}
