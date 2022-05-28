package Users;

import Persistent.Traceable;

import java.io.Serializable;

public abstract class User implements Traceable, Serializable {

	private static final long serialVersionUID = 1L;

	private final long uid;
	private String username;
	private String password;
	private String fullName;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public User(long uid, String username, String password, String fullName) {
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.fullName = fullName;
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	@Override
	public long getId() {
		return uid;
	}

	@Deprecated
	public long getUid() {
		return uid;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFullName() {
		return fullName;
	}

	public User setUsername(String username) {
		this.username = username;
		return this;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public User setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}
	
}
