package ru.geekbrains.springdata.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.geekbrains.springdata.dto.ProductDto;
import ru.geekbrains.springdata.exceptions.ResourceNotFoundException;
import ru.geekbrains.springdata.utils.FavoriteProducts;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class FavoriteProductService {

	private final ProductService productService;
	private final RedisTemplate<String, Object> objectRedisTemplate;

	private static final String REDIS_FAVORITE_PREFIX = "MY_FAVORITE_PRODUCTS_";

	private String getKey(Principal principal, String uuid) {
		if (principal != null) {
			return REDIS_FAVORITE_PREFIX + principal.getName();
		}
		return REDIS_FAVORITE_PREFIX + uuid;
	}

	public FavoriteProducts getFavoriteForCurrentUser(Principal principal, String uuid) {
		String key = getKey(principal, uuid);
		if (!objectRedisTemplate.hasKey(key)) {
			objectRedisTemplate.opsForValue().set(key, new FavoriteProducts());
		}
		return (FavoriteProducts) objectRedisTemplate.opsForValue().get(key);
	}

	public void addItem(Principal principal, String uuid, Long productId) {
		FavoriteProducts favorite = getFavoriteForCurrentUser(principal, uuid);
		ProductDto product = productService.findById(productId).map(ProductDto::new).orElseThrow(() -> new ResourceNotFoundException("Невозможно добавить продукт в избранное, так как продукт с id: " + productId + " не существует"));
		favorite.add(product);
		update(principal, uuid, favorite);
	}

	public void update(Principal principal, String uuid, FavoriteProducts favoriteProducts) {
		String cartKey = getKey(principal, uuid);
		objectRedisTemplate.opsForValue().set(cartKey, favoriteProducts);
	}

	public void removeFavorite(Principal principal, String uuid, Long productId) {
		FavoriteProducts favorite = getFavoriteForCurrentUser(principal, uuid);
		favorite.remove(productId);
		update(principal, uuid, favorite);
	}

	public void removeAll(Principal principal, String uuid) {
		FavoriteProducts favorite = getFavoriteForCurrentUser(principal, uuid);
		favorite.clear();
		update(principal, uuid, favorite);
	}
}
