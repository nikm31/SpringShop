package ru.geekbrains.springdata.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.geekbrains.springshop.api.dto.CartDto;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
	private final WebClient cartServiceWebClient;

	public CartDto getCurrentCart(String uuid) {
		return cartServiceWebClient.get()
				.uri("/api/v1/cart/" + uuid)
				.retrieve()
				.bodyToMono(CartDto.class)
				.block();
	}

	public void clear(String uuid) {
		cartServiceWebClient.get()
				.uri("/api/v1/cart/clear/" + uuid )
				.retrieve()
				.toBodilessEntity()
				.block();
	}
}
