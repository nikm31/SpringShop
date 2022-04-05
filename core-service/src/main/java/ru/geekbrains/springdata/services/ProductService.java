package ru.geekbrains.springdata.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.springdata.entity.Category;
import ru.geekbrains.springdata.entity.Product;
import ru.geekbrains.springdata.repositories.ProductRepo;
import ru.geekbrains.springdata.specifications.ProductSpecs;
import ru.geekbrains.springshop.api.dto.ProductDto;
import ru.geekbrains.springshop.api.exeptions.ResourceNotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepo productRepo;
	private final CategoryService categoryService;

	public Page<Product> getProductsWithPagingAndFiltering(int pageNumber, int pageSize, String title, Double minPrice, Double maxPrice) {

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

		return productRepo.findAll(spec, PageRequest.of(pageNumber, pageSize));
	}

	public Page<Product> findAll(int pageIndex, int pageSize) {
		return productRepo.findAll(PageRequest.of(pageIndex, pageSize));
	}

	@Transactional
	public void deleteById(Long id) {
		findById(id).orElseThrow(() -> new ResourceNotFoundException("Product id = " + id + " not found"));
		productRepo.deleteById(id);
	}

	public Optional<Product> findById(Long id) {
		return productRepo.findById(id);
	}

	public Product save(Product productDto) {
		Product product = new Product();
		product.setTitle(productDto.getTitle());
		product.setPrice(productDto.getPrice());
		Category category = categoryService.findByTitle(productDto.getCategory().getTitle()).orElseThrow(() -> new ResourceNotFoundException("Category title = " + " not found"));
		product.setCategory(category);
		return productRepo.save(product);
	}

	@Transactional
	public void updateProductFromDto(ProductDto productDto) {
		Product product = findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Product id = " + productDto.getId() + " not found"));
		product.setPrice(productDto.getPrice());
		product.setTitle(productDto.getTitle());
		if (!product.getCategory().getTitle().equals(productDto.getCategoryTitle())) {
			Category category = categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(() -> new ResourceNotFoundException("Category title = " + productDto.getCategoryTitle() + " not found"));
			product.setCategory(category);
		}
	}

}
