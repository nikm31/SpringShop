package ru.geekbrains.springdata;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import ru.geekbrains.springshop.api.dto.ProductDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FullProductServiceTest {
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void getProductByIdTest() {
		ProductDto forObject = testRestTemplate.getForObject("/api/v1/products/1", ProductDto.class);
		assertThat(forObject).isNotNull();
	}
}
