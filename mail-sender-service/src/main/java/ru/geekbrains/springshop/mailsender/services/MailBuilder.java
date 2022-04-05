package ru.geekbrains.springshop.mailsender.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class MailBuilder {
	private final TemplateEngine templateEngine;

	public String buildDefaultEmail() {
		Context context = new Context();
		return templateEngine.process("defaultMailTemplate", context);
	}
}
