package ru.geekbrains.springshop.cart.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;
import ru.geekbrains.springshop.cart.properties.ProductServiceIntegrationProperties;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(
		ProductServiceIntegrationProperties.class
)
@RequiredArgsConstructor
public class ServiceConfig {
	private final ProductServiceIntegrationProperties productServiceIntegrationProperties;

	@Bean
	public WebClient cartServiceWebClient() {
		TcpClient tcpClient = TcpClient
				.create()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, productServiceIntegrationProperties.getConnectionTimeout())
				.doOnConnected(connection -> {
					connection.addHandlerLast(new ReadTimeoutHandler(productServiceIntegrationProperties.getReadTimeout(), TimeUnit.MILLISECONDS));
					connection.addHandlerLast(new WriteTimeoutHandler(productServiceIntegrationProperties.getWriteTimeout(), TimeUnit.MILLISECONDS));
				});

		return WebClient
				.builder()
				.baseUrl(productServiceIntegrationProperties.getUrl())
				.clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
				.build();
	}
}
