package ru.geekbrains.springdata.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springdata.converters.ProductConverter;
import ru.geekbrains.springdata.entity.Product;
import ru.geekbrains.springdata.services.ProductService;
import ru.geekbrains.springshop.api.dto.PageDto;
import ru.geekbrains.springshop.api.dto.ProductDto;
import ru.geekbrains.springshop.api.exeptions.ResourceNotFoundException;
import ru.geekbrains.springshop.api.exeptions.ShopError;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Продукты", description = "Методы работы с продуктами")
public class ProductController {
	private final ProductService productService;
	private final ProductConverter productConverter;

	@Operation(
			summary = "Получение страницы продуктов с фильтрами",
			responses = {
					@ApiResponse(
							description = "Успешный ответ", responseCode = "200",
							content = @Content(schema = @Schema(implementation = PageDto.class))
					)
			}
	)
	@GetMapping
	public PageDto<ProductDto> findAllWithFilters(
			@RequestParam(value = "page") Optional<Integer> page,
			@RequestParam(value = "pageSize") Optional<Integer> pageSize,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "minPrice", required = false) Double minPrice,
			@RequestParam(value = "maxPrice", required = false) Double maxPrice
	) {
		final int currentPageSize = pageSize.orElse(0) < 5 ? 5 : pageSize.get();
		final int currentPage = page.orElse(0) < 1 ? 0 : page.get() - 1;

		Page<Product> pageProduct = productService.getProductsWithPagingAndFiltering(currentPage, currentPageSize, title, minPrice, maxPrice);
		return productConverter.pageToDto(pageProduct);
	}

	@Operation(
			summary = "Запрос на получение продукта по id",
			responses = {
					@ApiResponse(
							description = "Успешный ответ", responseCode = "200",
							content = @Content(schema = @Schema(implementation = ProductDto.class))
					),
					@ApiResponse(
							description = "Продукт не найден", responseCode = "404",
							content = @Content(schema = @Schema(implementation = ShopError.class))
					)
			}
	)
	@GetMapping(path = "/{id}")
	public ProductDto findById(@PathVariable @Parameter(description = "ID продукта", required = true) Long id) {
		return productConverter.entityToDto(productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product id = " + id + " not found")));
	}


	@Operation(
			summary = "Удаление продукта по id",
			responses = {
					@ApiResponse(
							description = "Успешный ответ", responseCode = "200",
							content = @Content(schema = @Schema(implementation = ProductDto.class))
					),
					@ApiResponse(
							description = "Продукт не найден", responseCode = "404",
							content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))
					)
			}
	)
	@DeleteMapping(path = "/{id}")
	public void deleteById(@PathVariable @Parameter(description = "ID продукта", required = true) Long id) {
		productService.deleteById(id);
	}

	@Operation (
			summary = "Изменение информации о продукте",
			description = "метод не возвращает JSON",
			responses = {
					@ApiResponse(
							description = "Успешная операция", responseCode = "200"
					)
			}
	)
	@PutMapping
	public void changeProduct(@RequestBody @Parameter(description = "Товар для удаления", required = true) ProductDto productDto) {
		productService.updateProductFromDto(productDto);
	}


//	@PostMapping
//	public Product addProduct(@ModelAttribute AddNewProductForm form) {
//		Product product = new Product();
//		product.setTitle(form.getTitle());
//		product.setPrice(BigDecimal.valueOf(form.getPrice()));
//		product.setCategory(categoryService.findByTitle(form.getCategory()).orElseThrow(() -> new ResourceNotFoundException("Category not found id")));
//		if (!form.getFile().isEmpty()) {
//			String pathToSavedImage = imageSaverService.saveFile(form.getFile());
//			ProductImage productImage = new ProductImage();
//			productImage.setPath(pathToSavedImage);
//			productImage.setProduct(product);
//			product.addImage(productImage);
//		}
//		return productService.save(product);
//	}


}
