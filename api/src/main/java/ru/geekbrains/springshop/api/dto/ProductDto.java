package ru.geekbrains.springshop.api.dto;

import java.math.BigDecimal;

public class ProductDto {
	private Long id;

	private String title;

	private BigDecimal price;

	private String categoryTitle;

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
