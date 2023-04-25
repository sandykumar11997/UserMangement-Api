package in.happy.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.happy.binding.ActivateUser;
import in.happy.binding.Login;
import in.happy.binding.User;
import in.happy.service.UserService;



@RestController
public class UserRestController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/save")
	public ResponseEntity<String> savedUser(@RequestBody User user){
		boolean saveUser = userService.saveUser(user);
		if(saveUser) {
			return new ResponseEntity<>("usersaved successfully" , HttpStatus.OK);
		}else
			return new ResponseEntity<>("registration failed" , HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/activate")
	public ResponseEntity<String> activateUserd(@RequestBody ActivateUser activateUser){
		boolean activate = userService.activateUser(activateUser);
		if(activate) {
			return new ResponseEntity<>("account activated" , HttpStatus.OK);
		}else {
			return new ResponseEntity<>("account inactive" , HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> allUser = userService.getAllUser();
		return new ResponseEntity<>(allUser , HttpStatus.OK);
	}
	
	@GetMapping("/editUser/{userId}")
	public ResponseEntity<User> editUser(@PathVariable Integer userId){
		User userById = userService.getUserById(userId);
		return new ResponseEntity<>(userById , HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Integer userId){
		boolean isdelete = userService.deleteUserById(userId);
		if(isdelete) {
			return new ResponseEntity<>("user deleted successfully" , HttpStatus.OK);
		}else {
			return new ResponseEntity<>("delete not successfull" , HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/accountStatus/{userId}/{status}")
	public ResponseEntity<String> accountStatus(@PathVariable Integer userId , @PathVariable String status){
		
		boolean accStatus = userService.accStatus(userId, status);
		if(accStatus) {
			return new ResponseEntity<>("status changed" , HttpStatus.OK);
		}else {
			return new ResponseEntity<>("status not changed" , HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Login login){
		String login2 = userService.login(login);
		return new ResponseEntity<>(login2 , HttpStatus.OK);
	}
	
	@GetMapping("/forgotPwd/{email}")
	public ResponseEntity<String> forgotPwd(@PathVariable String email){
		String forgotPassword = userService.forgotPassword(email);
		return new ResponseEntity<>(forgotPassword , HttpStatus.OK);
	}

	
	

}
