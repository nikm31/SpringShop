package ru.geekbrains.springshop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Страница с товарами")
public class PageDto<E> {

	@Schema(description = "Список отфильтрованных товаров")
	private List<E> content;

	@Schema(description = "Страница товарами", example = "0")
	private int page;

	@Schema(description = "Количество страниц")
	private int totalPages;

	public List<E> getContent() {
		return content;
	}

	public void setContent(List<E> content) {
		this.content = content;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public PageDto() {
	}
}
