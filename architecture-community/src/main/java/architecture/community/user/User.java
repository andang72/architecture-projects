package architecture.community.user;

public interface User {

	enum Status {

		NONE(0),

		APPROVED(1),

		REJECTED(2),

		VALIDATED(3),

		REGISTERED(4);

		int id;

		public int getId() {
			return id;
		}

		private Status(int id) {
			this.id = id;
		}

		public static Status getById(int i) {
			for (Status status : values()) {
				if (status.getId() == i)
					return status;
			}
			return NONE;
		}
	}

	public long getUserId();

	public String getUsername();

	public String getName();

	public boolean isEnabled();
	
	public boolean isNameVisible();
	
	public boolean isEmailVisible();
	
	public Status getStatus();

	public String getPassword();
	
	public boolean isAnonymous() ;
	
	public String getEmail();
	
}
