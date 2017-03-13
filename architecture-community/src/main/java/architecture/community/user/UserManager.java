package architecture.community.user;

import java.util.List;

public interface UserManager {

	
	public abstract User getUser(User template );
	
	public abstract User getUser(User template , boolean caseSensitive );
	
	public abstract User getUser(String username) throws UserNotFoundException;
	
	public abstract User getUser(long userId) throws UserNotFoundException;
	
	public abstract User createUser(User newUser) throws UserAlreadyExistsException, EmailAlreadyExistsException;
	
	public abstract void deleteUser(User user) throws UserNotFoundException ;
	
}
