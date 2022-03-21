package ru.geekbrains.springdata.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springdata.converters.ProductConverter;
import ru.geekbrains.springdata.entity.Product;
import ru.geekbrains.springdata.entity.ProductImage;
import ru.geekbrains.springdata.forms.AddNewProductForm;
import ru.geekbrains.springdata.services.CategoryService;
import ru.geekbrains.springdata.services.ImageSaverService;
import ru.geekbrains.springdata.services.ProductService;
import ru.geekbrains.springdata.specifications.ProductSpecs;
import ru.geekbrains.springshop.api.dto.ProductDto;
import ru.geekbrains.springshop.api.exeptions.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;
	private final CategoryService categoryService;
	private final ImageSaverService imageSaverService;
	private final ProductConverter productConverter;

	@GetMapping
	public Page<ProductDto> findAllWithFilters(
			@RequestParam(value = "page") Optional<Integer> page,
			@RequestParam(value = "pageSize") Optional<Integer> pageSize,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "minPrice", required = false) Double minPrice,
			@RequestParam(value = "maxPrice", required = false) Double maxPrice
	) {
		final int currentPageSize = pageSize.orElse(0) < 5 ? 5 : pageSize.get();
		final int currentPage = page.orElse(0) < 1 ? 0 : page.get() - 1;
		Specification<Product> spec = Specification.where(null);
		StringBuilder filter = new StringBuilder();
		if (title != null) {
			spec = spec.and(ProductSpecs.titleContains(title));
			filter.append("&title=" + title);
		}
		if (minPrice != null) {
			spec = spec.and(ProductSpecs.priceGreaterThanOrEq(minPrice));
			filter.append("&minPrice=" + minPrice);
		}
		if (maxPrice != null) {
			spec = spec.and(ProductSpecs.priceLesserThanOrEq(maxPrice));
			filter.append("&maxPrice=" + minPrice);
		}
		return productService.getProductsWithPagingAndFiltering(currentPage, currentPageSize, spec).map(ProductConverter::entityToDto);
	}

	@GetMapping(path = "/{id}")
	public ProductDto findById(@PathVariable Long id) {
		return productConverter.entityToDto(productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product id = " + id + " not found")));
	}

	@Secured("ADMIN")
	@DeleteMapping(path = "/{id}")
	public ProductDto deleteById(@PathVariable Long id) {
		ProductDto deleted = productConverter.entityToDto(productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product id = " + id + " not found")));
		productService.deleteById(id);
		return deleted;
	}

	@Secured("ADMIN")
	@PutMapping
	public void changeProduct(@RequestBody ProductDto productDto) {
		productService.updateProductFromDto(productDto);
	}

	@Secured("ADMIN")
	@PostMapping
	public Product addProduct(@ModelAttribute AddNewProductForm form) {
		Product product = new Product();
		product.setTitle(form.getTitle());
		product.setPrice(BigDecimal.valueOf(form.getPrice()));
		product.setCategory(categoryService.findByTitle(form.getCategory()).orElseThrow(() -> new ResourceNotFoundException("Category not found id")));
		if (!form.getFile().isEmpty()) {
			String pathToSavedImage = imageSaverService.saveFile(form.getFile());
			ProductImage productImage = new ProductImage();
			productImage.setPath(pathToSavedImage);
			productImage.setProduct(product);
			product.addImage(productImage);
		}
		return productService.save(product);
	}


}
