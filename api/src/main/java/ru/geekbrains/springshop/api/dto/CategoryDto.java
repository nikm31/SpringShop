package ru.geekbrains.springshop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Информация с категориями товаров")
public class CategoryDto {

    @Schema(description = "ID категории", example = "1")
    private Long id;

    @Schema(description = "Название категории", example = "Процессор")
    private String title;

    @Schema(description = "Список товаров категории")
    private List<ProductDto> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}
