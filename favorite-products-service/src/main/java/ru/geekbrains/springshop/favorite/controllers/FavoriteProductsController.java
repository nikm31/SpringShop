package ru.geekbrains.springshop.favorite.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.springshop.favorite.sevices.FavoriteProductService;
import ru.geekbrains.springshop.favorite.utils.FavoriteProducts;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/favorite")
@RequiredArgsConstructor
public class FavoriteProductsController {
	private final FavoriteProductService favoriteProductService;

	@GetMapping("/{uuid}/add/{productId}")
	public void addToFavorite(Principal principal, @PathVariable String uuid, @PathVariable Long productId) {
		favoriteProductService.addItem(principal, uuid, productId);
	}

	@GetMapping("/{uuid}")
	public FavoriteProducts getFavoriteForCurrentUser(Principal principal, @PathVariable String uuid) {
		return favoriteProductService.getFavoriteForCurrentUser(principal, uuid);
	}

	@GetMapping("/{uuid}/delete/{productId}")
	public void deleteItem(Principal principal, @PathVariable String uuid, @PathVariable Long productId) {
		favoriteProductService.removeFavorite(principal, uuid, productId);
	}

	@GetMapping("/{uuid}/delete")
	public void deleteAll(Principal principal, @PathVariable String uuid) {
		favoriteProductService.removeAll(principal, uuid);
	}
}
