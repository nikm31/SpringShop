package ru.geekbrains.springshop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Модель продукта")
public class ProductDto {

	@Schema(description = "ID продукта", required = true, example = "1")
	private Long id;

	@Schema(description = "Название продукта", required = true, maxLength = 255, minLength = 3, example = "Коробка конфет")
	private String title;

	@Schema(description = "Цена продукта", required = true, example = "800.00")
	private BigDecimal price;

	@Schema(description = "Категория продукта", required = true, example = "Процессор")
	private String categoryTitle;

	@Schema(description = "Путь к картинке продукта", example = "img1.jpg")
	private String mainImagePath;

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public String getMainImagePath() {
		return mainImagePath;
	}

	public void setMainImagePath(String mainImagePath) {
		this.mainImagePath = mainImagePath;
	}

	public ProductDto(Long id, String title, BigDecimal price, String categoryTitle, String mainImagePath) {
		this.id = id;
		this.title = title;
		this.price = price;
		this.categoryTitle = categoryTitle;
		this.mainImagePath = mainImagePath;
	}

	public ProductDto() {
	}
}
