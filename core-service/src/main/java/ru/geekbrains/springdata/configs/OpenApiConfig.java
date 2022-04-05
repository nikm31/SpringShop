package ru.geekbrains.springdata.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
	@Bean
	public OpenAPI api() {
		return new OpenAPI()
				.info(
						new Info()
								.title("API работы с продуктами")
								.description("МС для работы с товарами")
								.version("1")
								.contact(
										new Contact()
												.email("firewather@gmail.com")
												.name("Nikolay")
								)
				);
	}
}
