package ru.geekbrains.springshop.favorite.integrations;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.geekbrains.springshop.api.dto.ProductDto;
import ru.geekbrains.springshop.api.exeptions.ResourceNotFoundException;

@Component
@RequiredArgsConstructor
@Data
public class ProductServiceIntegration {
	private final WebClient productServiceWebClient;

	public ProductDto getProductById(Long id) {
		return productServiceWebClient.get()
				.uri("/api/v1/products/" + id)
				.retrieve()
				.onStatus(
						httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(),
						clientResponse -> Mono.error(new ResourceNotFoundException("Товар не найден id: " + id))
				)
				.bodyToMono(ProductDto.class)
				.block();
	}
}
