package architecture.community.user.dao;

import architecture.community.user.User;

public interface UserDao {
	
	public abstract User createUser(User template);

	public abstract User getUserById(long userId);
		
	public abstract User getUserByUsername(String username);
	
	public abstract User getUserByEmail(String email);
	
	public abstract long getNextUserId();
	
	public abstract void deleteUser(User user);
	
}
