package architecture.community.user;

import java.util.Date;

public class DefaultRole implements Role {
	
	private long roleId;
	
	private String name;
	
	private String description;	

	private Date creationDate;

	private Date modifiedDate;
	
	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public String toString() {
		return "DefaultRole [roleId=" + roleId + ", name=" + name + ", description=" + description + ", creationDate="
				+ creationDate + ", modifiedDate=" + modifiedDate + "]";
	}

	
}
