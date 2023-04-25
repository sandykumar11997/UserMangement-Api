package in.happy.service;

import java.util.List;

import in.happy.binding.ActivateUser;
import in.happy.binding.Login;
import in.happy.binding.User;

public interface UserService {
	
	public boolean saveUser(User user);
	public boolean activateUser(ActivateUser activateUser);
	public List<User> getAllUser();
	public User getUserById(Integer userId);
	public boolean deleteUserById(Integer userId);
	public boolean accStatus(Integer userId,String status);
	public String login(Login login);
	public String forgotPassword(String email); 
	

}
