package ru.geekbrains.springshop.favorite.sevices;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.geekbrains.springshop.api.dto.ProductDto;
import ru.geekbrains.springshop.favorite.integrations.ProductServiceIntegration;
import ru.geekbrains.springshop.favorite.utils.FavoriteProducts;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class FavoriteProductService {

	private final ProductServiceIntegration productService;
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
		ProductDto product = productService.getProductById(productId);
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
