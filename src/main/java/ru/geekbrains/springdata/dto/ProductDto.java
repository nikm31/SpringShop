package ru.geekbrains.springdata.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.geekbrains.springdata.entity.Product;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class ProductDto {
	private Long id;

	@Length(min = 3, max = 255, message = "длинна 1-255 символов")
	private String title;

	@Min(value = 1, message = "цена не меньше 1 рубля")
	private int price;

	private String categoryTitle;

	private String mainImagePath;

	public ProductDto(Product product) {
		this.id = product.getId();
		this.title = product.getTitle();
		this.price = product.getPrice();
		this.categoryTitle = product.getCategory().getTitle();
		this.mainImagePath = product.getMainImagePath();
	}

}
