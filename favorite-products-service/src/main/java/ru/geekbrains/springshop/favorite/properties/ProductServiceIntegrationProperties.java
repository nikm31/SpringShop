package ru.geekbrains.springshop.favorite.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Data
@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.product-service")
public class ProductServiceIntegrationProperties {
	private String url;
	private Integer connectionTimeout;
	private Integer readTimeout;
	private Integer writeTimeout;
}
