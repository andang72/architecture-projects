package architecture.community.user;

import java.io.Serializable;
import java.util.Date;

import org.springframework.util.StringUtils;

public class UserTemplate implements User, Serializable {

	public static final User ANONYMOUS = new UserTemplate(-1L, "ANONYMOUS");

	private long userId;

	private String username;

	private String name;

	private Status status;

	private String email;

	private String firstName;

	private String lastName;

	private String password;

	private String passwordHash;

	private boolean enabled;

	private boolean nameVisible;

	private boolean emailVisible;

	private Date creationDate;

	private Date modifiedDate;

	public UserTemplate(User user) {
		if (null == user)
			return;
		userId = user.getUserId();
		username = user.getUsername();
		name = user.getName();
		email = user.getEmail();
		nameVisible = user.isNameVisible();
		emailVisible = user.isEmailVisible();
		creationDate = user.getCreationDate();
		modifiedDate = user.getModifiedDate();
		status = user.getStatus();
		password = user.getPassword();
		passwordHash = user.getPasswordHash();
		status = Status.NONE;
	}

	public UserTemplate() {
		userId = -2L;
		status = Status.NONE;
	}

	public UserTemplate(String username) {
		this.userId = -2L;
		this.username = username;
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

	public UserTemplate(long userId) {
		this.userId = userId;
	}

	public UserTemplate(long userId, String username) {
		this.userId = userId;
		this.username = username;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		if (lastName != null && firstName != null) {
			StringBuilder builder = new StringBuilder(firstName);
			builder.append(" ").append(lastName);
			return builder.toString();
		} else {
			return name;
		}
	}

	public void setName(String name) {
		if (lastName != null && firstName != null && name != null) {
			name = name.trim();
			int index = name.indexOf(" ");
			if (index > -1) {
				firstName = name.substring(0, index);
				lastName = name.substring(index + 1, name.length());
				lastName = lastName.trim();
				this.name = null;
			} else {
				firstName = null;
				lastName = null;
				this.name = name;
			}
		} else {
			this.name = name;
		}
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isNameVisible() {
		return nameVisible;
	}

	public void setNameVisible(boolean nameVisible) {
		this.nameVisible = nameVisible;
	}

	public boolean isEmailVisible() {
		return emailVisible;
	}

	public void setEmailVisible(boolean emailVisible) {
		this.emailVisible = emailVisible;
	}

	public boolean isAnonymous() {
		if (this.userId == -1L)
			return true;
		else
			return false;
	}

}
