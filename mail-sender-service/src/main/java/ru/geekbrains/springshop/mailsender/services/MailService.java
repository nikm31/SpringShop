package ru.geekbrains.springshop.mailsender.services;

import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@AllArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;
	private final MailBuilder mailBuilder;
	private ExecutorService executorService;

	@PostConstruct
	private void init() {
		executorService = Executors.newSingleThreadExecutor();
	}

	public void sendSimpleEmail(String toAddress, String subject) {
		try {
			String message = mailBuilder.buildDefaultEmail();
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			messageHelper.setTo(toAddress);
			messageHelper.setSubject(subject);
			messageHelper.setText(message, true);
			FileSystemResource file = new FileSystemResource(ResourceUtils.getFile("classpath:logo.png"));
			messageHelper.addAttachment("logo.png", file);
			executorService.submit(() -> mailSender.send(mimeMessage));
		} catch (FileNotFoundException | MessagingException e) {
			e.printStackTrace();
		}

	}
}
