package in.happy.utils;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class UserMailService {
	
	@Autowired
	public JavaMailSender javaMailSender;
	
	
	
	public boolean mailGenerater(String to,String subject,String body){
		 boolean isMailSent = false;
		 try {
			 MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			 MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			 helper.setTo(to);
			 helper.setSubject(subject);
			 helper.setText(body, true);
			 javaMailSender.send(mimeMessage);
			 isMailSent = true;
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 return isMailSent;
		
	}

}
