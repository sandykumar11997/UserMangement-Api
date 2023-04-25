package in.happy.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.happy.binding.ActivateUser;
import in.happy.binding.Login;
import in.happy.binding.User;
import in.happy.entity.UserMaster;
import in.happy.repo.UserMasterRepo;
import in.happy.utils.UserMailService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMasterRepo userMasterrepo;
	
	@Autowired
	private UserMailService emailUtils;

	@Override
	public boolean saveUser(User user) {
		UserMaster entity = new UserMaster();
		BeanUtils.copyProperties(user, entity);
		entity.setPassword(getRandomPassword());
		UserMaster save = userMasterrepo.save(entity);
		String subject = " registration email";
		
		String fileName = "RegBody.txt";
		String body = regReadEmail(entity.getName(), entity.getPassword(),fileName);
		
		emailUtils.mailGenerater(entity.getMail(), subject, body);
		
		return save.getUserId() != null;
	}

	@Override
	public boolean activateUser(ActivateUser activateUser) {
		UserMaster entity = new UserMaster();
		entity.setMail(activateUser.getMail());
		entity.setPassword(activateUser.getPassword());
		Example<UserMaster> of = Example.of(entity);
		List<UserMaster> findAll = userMasterrepo.findAll(of);
		if(findAll.isEmpty()) {
			return false;
		}else {
			UserMaster userMaster = findAll.get(0);
			userMaster.setPassword(activateUser.getPassword());
			userMaster.setStatus("Active");
			return true;
		}
	}

	@Override
	public List<User> getAllUser() {
		List<UserMaster> findAll = userMasterrepo.findAll();
		List<User> users = new ArrayList<>();
		for(UserMaster entity : findAll) {
			User user = new User();
			BeanUtils.copyProperties(entity, users);
			users.add(user);
		}
		
		return users;
	}

	@Override
	public User getUserById(Integer userId) {
		Optional<UserMaster> findById = userMasterrepo.findById(userId);
		if(findById.isPresent()) {
			User user = new User();
			UserMaster userMaster = findById.get();
			BeanUtils.copyProperties(userMaster, user);
			return user;
		}
		return null;
	}

	@Override
	public boolean deleteUserById(Integer userId) {
		try {
			userMasterrepo.deleteById(userId);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean accStatus(Integer userId, String status) {
		Optional<UserMaster> findById = userMasterrepo.findById(userId);
		if(findById.isPresent()) {
			UserMaster userMaster = findById.get();
			userMaster.setStatus(status);
			userMasterrepo.save(userMaster);
			return true;
		}
		return false;
	}

	@Override
	public String login(Login login) {
		String mail = login.getMail();
		String password = login.getPassword();
		UserMaster user = new UserMaster();
		user.setMail(mail);
		user.setPassword(password);
		Example<UserMaster> of = Example.of(user);
		List<UserMaster> findAll = userMasterrepo.findAll(of);
	    if(findAll.isEmpty()) {
	    	return "invalid credential";
	    }else {
	    	UserMaster userMaster = findAll.get(0);
	    	if(userMaster.getStatus().equals("active")) {
	    		return "login success";
	    	}else {
	    		return "account not activated";
	    	}
	    }
	}

	@Override
	public String forgotPassword(String email) {
		UserMaster entity = userMasterrepo.findByMail(email);
		if(entity == null) {
			return "invalid email";
		}
		String fileName ="ForgotMail.txt";
		String body = regReadEmail(entity.getName(), entity.getPassword(), fileName) ;
		boolean mailsent = emailUtils.mailGenerater(email, fileName, body);
		if(mailsent) {
			return "Password Sent to your email";
		}
		return null;
	}
	private String getRandomPassword() {
		
		    String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		    String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
		    String numbers = "0123456789";
		    String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

		    StringBuilder sb = new StringBuilder();
		    Random random = new Random();
		    int length = 6;

		    for(int i = 0; i < length; i++) {
		      int index = random.nextInt(alphaNumeric.length());
		      char randomChar = alphaNumeric.charAt(index);
		      sb.append(randomChar);
		    }

		    return sb.toString();
		  }
	
	private String regReadEmail(String name, String password,String fileName){
		
		String mailBody = null;
		String url="";
		try {
			
			
		    FileReader fr = new FileReader(fileName);
		    BufferedReader br = new BufferedReader(fr);
			StringBuilder sb = new StringBuilder();
			
			
			String line = br.readLine();
			while(line != null) {
				sb.append(line);
				line=br.readLine();
			}
			br.close();
			mailBody= sb.toString();
			mailBody=mailBody.replace("{name}", name);
			mailBody=mailBody.replace("{password}", password);
			mailBody=mailBody.replace("{url}", url);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return mailBody;
	}
	}


