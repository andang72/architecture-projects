package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import architecture.community.user.dao.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("WebContent/")
@ContextConfiguration(locations={"classpath:application-context.xml", "classpath:application-user-context.xml"})
public class UserManagerTest {

	@Autowired
    private UserDao userDao;
	
	private static Logger log = LoggerFactory.getLogger(UserManagerTest.class);

	@Test
	public void testUserDaoById(){
		log.debug("" + userDao.getUserById(1));
	}
	
	@Test
	public void testUserDaoByUsername(){
		log.debug(">>>>>>>>>>>." + userDao.getUserByUsername("test") );
	}
}
