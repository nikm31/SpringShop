package ru.geekbrains.springshop.mailsender.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springshop.api.enums.MailSubject;
import ru.geekbrains.springshop.mailsender.services.MailService;

@RestController
@RequestMapping("/api/v1/mail-sender")
@RequiredArgsConstructor
public class MailController {

	@Autowired
	MailService mailService;

	@GetMapping(value = "/{subject}/{user-email}")
	public @ResponseBody ResponseEntity sendSimpleEmail(@PathVariable("subject") MailSubject subject, @PathVariable("user-email") String email) {
		try {
			mailService.sendSimpleEmail(email, subject.name());
		} catch (MailException mailException) {
			return new ResponseEntity<>("Не удалось отправить сообщение", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>("Сообщение отправлено", HttpStatus.OK);
	}

}
