package ru.geekbrains.springshop.cart;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.geekbrains.springshop.api.dto.ProductDto;
import ru.geekbrains.springshop.cart.entity.Cart;
import ru.geekbrains.springshop.cart.services.CartService;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	CartService cartService;

	@Test
	void getCart() throws Exception {
		Cart cart = new Cart();
		cart.add(new ProductDto(1L, "INTEL 1 GB", BigDecimal.valueOf(1000), "RAM", "1.jpeg"));
		cart.add(new ProductDto(2L, "INTEL 2 GB", BigDecimal.valueOf(2000), "RAM", "1.jpeg"));

		given(cartService.getCartForCurrentUser(null, "UUID")).willReturn(cart);

		mvc
				.perform(
						get("/api/v1/cart/" + "UUID")
								.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isOk());
//				.andExpect(jsonPath("$").isArray()); TODO: тут ошибка
//				.andExpect(jsonPath("$", hasSize(2))); TODO: тут ошибка
	}
}
