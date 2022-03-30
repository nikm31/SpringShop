package ru.geekbrains.springdata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import ru.geekbrains.springdata.entity.Product;
import ru.geekbrains.springdata.services.ProductService;

import java.util.Optional;

@SpringBootTest(classes = ProductService.class)
public class ProductTest {

	@Autowired
	private ProductService productService;

	@Test
	public void getAllProducts() {
		Page<Product> products = productService.findAll(1,5);
		Assertions.assertEquals(5, products.getSize());
	}

	@Test
	public void findProductById(){
		Optional<Product> product = productService.findById(1L);
		Assertions.assertEquals(product.get().getId(), 1);
	}


}
