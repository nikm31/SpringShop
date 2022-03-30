package ru.geekbrains.springdata.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.springdata.entity.Category;
import ru.geekbrains.springdata.entity.Product;

import java.util.List;

@Data
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String title;
    private List<Product> products;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.products = category.getProducts();
    }
}
