package ru.geekbrains.springdata.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.geekbrains.springdata.entity.Category;
import ru.geekbrains.springdata.entity.Product;
import ru.geekbrains.springdata.services.CategoryService;
import ru.geekbrains.springshop.api.dto.PageDto;
import ru.geekbrains.springshop.api.dto.ProductDto;
import ru.geekbrains.springshop.api.exeptions.ResourceNotFoundException;

@Component
@RequiredArgsConstructor
public class ProductConverter {
	private final CategoryService categoryService;

	public static ProductDto entityToDto(Product product) {
		return new ProductDto(product.getId(), product.getTitle(), product.getPrice(), product.getCategory().getTitle(), product.getMainImagePath());
	}


	public PageDto<ProductDto> pageToDto(Page<Product> productPage) {
		Page<ProductDto> productDto = productPage.map(ProductConverter::entityToDto);
		PageDto<ProductDto> products = new PageDto<>();
		products.setPage(productDto.getNumber());
		products.setContent(productDto.getContent());
		products.setTotalPages(productDto.getTotalPages());
		return products;
	}



	public Product dtoToEntity(ProductDto productDto) {
		Product product = new Product();
		product.setId(productDto.getId());
		product.setTitle(productDto.getTitle());
		product.setPrice(productDto.getPrice());
		Category c = categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(() -> new ResourceNotFoundException("Категория не найдена"));
		product.setCategory(c);
		product.setMainImagePath(product.getMainImagePath());
		return product;
	}
}
