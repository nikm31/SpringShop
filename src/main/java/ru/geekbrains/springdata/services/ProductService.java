package ru.geekbrains.springdata.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.springdata.dto.ProductDto;
import ru.geekbrains.springdata.entity.shop.Category;
import ru.geekbrains.springdata.entity.shop.Product;
import ru.geekbrains.springdata.exceptions.ResourceNotFoundException;
import ru.geekbrains.springdata.repositories.shop.ProductRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepo productRepo;
    private CategoryService categoryService;

    public ProductService(ProductRepo productRepo, CategoryService categoryService) {
        this.productRepo = productRepo;
        this.categoryService = categoryService;
    }

    public Page<Product> getProductsWithPagingAndFiltering(int pageNumber, int pageSize, Specification<Product> productSpecification) {
        return productRepo.findAll(productSpecification, PageRequest.of(pageNumber, pageSize));
    }

    public Page<Product> findAll(int pageIndex, int pageSize) {
        return productRepo.findAll(PageRequest.of(pageIndex, pageSize));
    }

    public void deleteById(Long id) {
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

    public List<ProductDto> listProductToDto(List<Product> products) {
        List<ProductDto> list = new ArrayList<>();
        for (Product p : products) {
            list.add(new ProductDto(p));
        }
        return list;
    }

}
