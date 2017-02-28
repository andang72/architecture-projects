package architecture.community.user;

public interface UserManager {

	public User getUser( User template );
	
	public User getUser( User template , boolean caseSensitive );
	
	public User getUser(String username) throws UserNotFoundException;
	
	public User getUser(long userId) throws UserNotFoundException;
	
}
