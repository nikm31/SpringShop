package ru.geekbrains.springdata.configs;

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
import ru.geekbrains.springdata.properties.AuthServiceIntegrationProperties;
import ru.geekbrains.springdata.properties.CartServiceIntegrationProperties;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(
		{CartServiceIntegrationProperties.class, AuthServiceIntegrationProperties.class}
)
@RequiredArgsConstructor
public class ServiceConfig {
	private final CartServiceIntegrationProperties cartServiceIntegrationProperties;
	private final AuthServiceIntegrationProperties authServiceIntegrationProperties;

	@Bean
	public WebClient cartServiceWebClient() {
		TcpClient tcpClient = TcpClient
				.create()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, cartServiceIntegrationProperties.getConnectTimeout())
				.doOnConnected(connection -> {
					connection.addHandlerLast(new ReadTimeoutHandler(cartServiceIntegrationProperties.getReadTimeout(), TimeUnit.MILLISECONDS));
					connection.addHandlerLast(new WriteTimeoutHandler(cartServiceIntegrationProperties.getWriteTimeout(), TimeUnit.MILLISECONDS));
				});

		return WebClient
				.builder()
				.baseUrl(cartServiceIntegrationProperties.getUrl())
				.clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
				.build();
	}

	@Bean
	public WebClient authServiceWebClient() {
		TcpClient tcpClient = TcpClient
				.create()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, authServiceIntegrationProperties.getConnectTimeout())
				.doOnConnected(connection -> {
					connection.addHandlerLast(new ReadTimeoutHandler(authServiceIntegrationProperties.getReadTimeout(), TimeUnit.MILLISECONDS));
					connection.addHandlerLast(new WriteTimeoutHandler(authServiceIntegrationProperties.getWriteTimeout(), TimeUnit.MILLISECONDS));
				});

		return WebClient
				.builder()
				.baseUrl(authServiceIntegrationProperties.getUrl())
				.clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
				.build();
	}
}
