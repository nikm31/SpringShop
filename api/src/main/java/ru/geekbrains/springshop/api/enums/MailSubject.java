package ru.geekbrains.springshop.api.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Список статусов потчы для выполнения")
public enum MailSubject {
	REGISTRATION,
	CHANGE_PASSWORD,
	ORDER_STATUS,
	PAYMENT_STATUS,
	NOTIFICATION
}
