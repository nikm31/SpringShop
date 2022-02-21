package ru.geekbrains.springdata.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.geekbrains.springdata.entity.Category;
import ru.geekbrains.springdata.repositories.CategoryRepo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryService {
	private final CategoryRepo categoryRepo;
	private final RedisTemplate<String, Object> redisTemplate;

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
	public List<String> getCategories() {
		String redisCategoryPrefix = "MY_MARKET_CATEGORY_";

		if (!redisTemplate.hasKey(redisCategoryPrefix)) {
			redisTemplate.opsForValue().set(redisCategoryPrefix, Collections.EMPTY_LIST);
		}

		redisTemplate.opsForValue().set(redisCategoryPrefix, categoryRepo.findAll().stream().map(Category::getTitle)
				.collect(Collectors.toList()));
		return (List<String>) redisTemplate.opsForValue().get(redisCategoryPrefix);
	}
}
