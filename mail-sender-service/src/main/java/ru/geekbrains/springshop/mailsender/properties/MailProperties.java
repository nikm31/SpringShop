package ru.geekbrains.springshop.mailsender.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Data
@ConstructorBinding
@ConfigurationProperties(prefix = "mail-sender")
public class MailProperties {
	private String host;
	private int port;
	private String username;
	private String password;
	private String protocol;
	private boolean auth;
	private boolean starttlsEnable;
	private boolean debug;
}
