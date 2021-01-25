package com.szakdoga.serviceimp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.szakdoga.entity.Kerelem;
import com.szakdoga.entity.User;
import com.szakdoga.service.EmailService;

@Service
public class EmailServiceImplementation implements EmailService{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String MAIL_FROM;
	
	@Autowired
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void SendValtMail(Kerelem kerelem) {
		SimpleMailMessage msg = null;

		try {
			msg = new SimpleMailMessage();
			msg.setFrom(MAIL_FROM);
			msg.setTo(kerelem.getEmail());
			msg.setSubject(kerelem.getTipus() + " változtatás");
			msg.setText("http://localhost:9001/"+kerelem.getTipus()+"/"+kerelem.getToken());
			mailSender.send(msg);
		}catch(Exception e) {
			logger.debug("mail: " + String.valueOf(e));
		}
	}
	
	public void SendAuthMail(User user) {
		SimpleMailMessage msg = null;

		try {
			msg = new SimpleMailMessage();
			msg.setFrom(MAIL_FROM);
			msg.setTo(user.getEmail());
			msg.setSubject("Visszaigazoló e-mail");
			msg.setText("http://localhost:9001/visszaigazolas/"+user.getToken());
			mailSender.send(msg);
		}catch(Exception e) {
			logger.debug("mail: " + String.valueOf(e));
		}
	}
	
}
