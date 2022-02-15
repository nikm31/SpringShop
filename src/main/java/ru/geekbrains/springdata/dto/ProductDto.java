package ru.geekbrains.springdata.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import ru.geekbrains.springdata.entity.shop.Product;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class ProductDto {
    private Long id;

    @NonNull
    @Length(min = 3, max = 255, message = "длинна 1-255 символов")
    private String title;

    @Min(value = 1, message = "ценва не меньше 1 рубля")
    private int price;

    @NonNull()
    private String categoryTitle;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.categoryTitle = product.getCategory().getTitle();
    }

}
