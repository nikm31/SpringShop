package ru.geekbrains.springshop.mailsender.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import ru.geekbrains.springshop.mailsender.properties.MailProperties;

import java.util.Properties;

@Configuration
@EnableConfigurationProperties(MailProperties.class)
@RequiredArgsConstructor
public class MailConfig {

	@Autowired
	private final MailProperties properties;

	@Bean
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(properties.getHost());
		mailSender.setPort(properties.getPort());
		mailSender.setUsername(properties.getUsername());
		mailSender.setPassword(properties.getPassword());

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.starttls.enable", properties.isStarttlsEnable());
		javaMailProperties.put("mail.smtp.auth", properties.isAuth());
		javaMailProperties.put("mail.transport.protocol", properties.getProtocol());
		javaMailProperties.put("mail.debug", properties.isDebug());

		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}
}
