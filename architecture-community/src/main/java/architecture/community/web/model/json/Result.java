package architecture.community.web.model.json;

import java.util.HashMap;

public class Result implements java.io.Serializable {

	
	private Error error;
	private boolean anonymous;
	private Integer count = 0;
	private HashMap<String, Object> data;

	public Result() {
		data = new HashMap<String, Object>();
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	public HashMap<String, Object> getData() {
		return data;
	}

	public static Result newResult() {

		Result r = new Result();
		//r.anonymous = SecurityHelper.getUser().isAnonymous();

		return r;
	}

	public static Result newResult(Throwable e) {
		Result r = new Result();
		r.error = new Error(e);
		//r.anonymous = SecurityHelper.getUser().isAnonymous();
		return r;
	}

	public boolean isAnonymous() {
		return anonymous;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
