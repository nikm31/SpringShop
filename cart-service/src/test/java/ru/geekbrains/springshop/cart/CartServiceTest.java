package ru.geekbrains.springshop.cart;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import ru.geekbrains.springshop.api.dto.ProductDto;
import ru.geekbrains.springshop.cart.entity.Cart;
import ru.geekbrains.springshop.cart.integrations.ProductServiceIntegration;
import ru.geekbrains.springshop.cart.services.CartService;

import java.math.BigDecimal;

@SpringBootTest
public class CartServiceTest {
	@Autowired
	private CartService cartService;

	@Autowired
	private RedisTemplate redisTemplate;
	private final String UUID = "45b55ae8-a8f8-11ec-b909-0242ac120002";

	@MockBean
	ProductServiceIntegration productService;

	@BeforeEach
	public void initCart() {
		cartService.clearCart(null, UUID);
	}

	@Test
	public void addToCart() {
		ProductDto ram = new ProductDto();
		ram.setId(1L);
		ram.setTitle("RAM 1 GB");
		ram.setPrice(BigDecimal.valueOf(1000));
		ram.setCategoryTitle("Memory");
		ram.setMainImagePath("img1.jpg");

		ProductDto monitor = new ProductDto();
		monitor.setId(2L);
		monitor.setTitle("LG 28");
		monitor.setPrice(BigDecimal.valueOf(1000));
		monitor.setCategoryTitle("Monitor");
		monitor.setMainImagePath("img2.jpg");

		Mockito.doReturn(ram).when(productService).getProductById(1L);
		Mockito.doReturn(monitor).when(productService).getProductById(2L);
		cartService.addItem(null, UUID, 1L);
		cartService.addItem(null, UUID, 1L);
		cartService.addItem(null, UUID, 2L);

		Mockito.verify(productService, Mockito.times(1)).getProductById(ArgumentMatchers.eq(1L));
		Assertions.assertEquals(2, cartService.getCartForCurrentUser(null, UUID).getItems().size());
	}

	@Test
	public Cart getCartForCurrentUser() {
		addToCart();
		Cart cart = (Cart) redisTemplate.opsForValue().get("MY_MARKET_CART_" + UUID);

		Assertions.assertEquals(BigDecimal.valueOf(3000), cart.getTotalPrice());
		Assertions.assertEquals(2, cart.getItems().get(0).getQuantity());
		Assertions.assertEquals(1, cart.getItems().get(1).getQuantity());

		return cart;
	}

	public RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public String getUUID() {
		return UUID;
	}

	public ProductServiceIntegration getProductService() {
		return productService;
	}

	public void setProductService(ProductServiceIntegration productService) {
		this.productService = productService;
	}

}
