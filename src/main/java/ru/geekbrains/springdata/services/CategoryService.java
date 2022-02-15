package ru.geekbrains.springdata.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.geekbrains.springdata.entity.shop.Category;
import ru.geekbrains.springdata.repositories.shop.CategoryRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CategoryService {
	private CategoryRepo categoryRepo;
	private List<String> categoriesList;

	public CategoryService(CategoryRepo categoryRepo) {
		this.categoryRepo = categoryRepo;
		this.categoriesList = new ArrayList<>();
	}

	public List<String> getCategoriesList() {
		return categoriesList;
	}

	public Optional<Category> findByTitle(String title) {
		return categoryRepo.findByTitle(title);
	}

	public Optional<Category> findById(Long id) {
		return categoryRepo.findById(id);
	}

	public Optional<Category> findByIdWithProducts(Long id) {
		return categoryRepo.findByIdWithProducts(id);
	}

	@Scheduled(fixedRate = 86400000)
	@Async
	public void getCategories() {
		categoriesList = categoryRepo.findAll().stream().map(Category::getTitle).collect(Collectors.toList());
	}
}
