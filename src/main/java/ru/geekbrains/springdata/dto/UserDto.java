package ru.geekbrains.springdata.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
	private String username;
	private String password;
	private String passwordConfirmation;
	private String email;
	private String secretQuestion;
	private String secretAnswer;
}
