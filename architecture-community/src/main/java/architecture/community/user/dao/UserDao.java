package architecture.community.user.dao;

import architecture.community.user.User;

public interface UserDao {

	public abstract User getUserById(long userId);
	
	public abstract User getUserByUsername(String name);
		
	public abstract User getUserByNameOrEmail(String email);
	
}
