package ru.geekbrains.springdata.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springdata.services.FavoriteProductService;
import ru.geekbrains.springdata.utils.FavoriteProducts;

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

	@GetMapping("/{uuid}/remove/{productId}")
	public void deleteItem(Principal principal, @PathVariable String uuid, @PathVariable Long productId) {
		favoriteProductService.removeFavorite(principal, uuid, productId);
	}

	@DeleteMapping("/{uuid}")
	public void deleteAll(Principal principal, @PathVariable String uuid) {
		favoriteProductService.removeAll(principal, uuid);
	}
}
