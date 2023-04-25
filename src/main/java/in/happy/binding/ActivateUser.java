package in.happy.binding;

import lombok.Data;

@Data
public class ActivateUser {
	
	private String mail;
	private String password;
	private String tempPassword;
	private String confirmPassword;

}
