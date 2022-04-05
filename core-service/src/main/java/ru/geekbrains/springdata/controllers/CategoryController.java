package ru.geekbrains.springdata.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.springdata.services.CategoryService;
import ru.geekbrains.springshop.api.dto.PageDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Категории", description = "Контроллер отображения категорий")
public class CategoryController {
	private final CategoryService categoryService;

	@Operation(
			summary = "Получение всех категорий",
			responses = {
					@ApiResponse(
							description = "Успешный ответ", responseCode = "200",
							content = @Content(schema = @Schema(implementation = List.class))
					)
			}
	)
	@GetMapping
	public List<String> getAllCategories() {
		return categoryService.getCategories();
	}
}
