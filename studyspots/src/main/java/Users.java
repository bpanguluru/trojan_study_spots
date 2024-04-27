import java.util.List;

public class Users {
	String username; 
	String password; 
	String email; 
	String confirmedPassword;
	double totalAccountValue; 
	List<Posts> userPosts;
	Users(String u, String p, String e) {
		username = u; 
		password = p; 
		email = e;
	}
	public String getConfirmedPassword() {
		return confirmedPassword;
	}
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}
	
	
}
