package architecture.community.user;

import java.util.Date;

public interface Role {

	public abstract long getRoleId();
	
	public abstract String getName();
	
	public abstract String getDescription();
	
	public Date getCreationDate();
	
	public Date getModifiedDate();
	
}
