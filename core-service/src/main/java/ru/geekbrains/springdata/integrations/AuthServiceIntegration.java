package ru.geekbrains.springdata.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.geekbrains.springshop.api.dto.UserDto;

@Component
@RequiredArgsConstructor
public class AuthServiceIntegration {
	private final WebClient authServiceWebClient;

	public UserDto findByUsername(String username) {
		return authServiceWebClient.get()
				.uri("/api/v1/account/" + username)
				.retrieve()
				.bodyToMono(UserDto.class)
				.block();
	}

}
