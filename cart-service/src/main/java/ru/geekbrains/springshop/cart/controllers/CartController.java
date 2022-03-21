package ru.geekbrains.springshop.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.springshop.api.dto.StringResponse;
import ru.geekbrains.springshop.cart.entity.Cart;
import ru.geekbrains.springshop.cart.services.CartService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/generate")
    public StringResponse generateCartUuid() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    @GetMapping("/{uuid}/merge")
    public void mergeCarts(Principal principal, @PathVariable String uuid) {
        cartService.merge(principal, uuid);
    }

    @GetMapping("/{uuid}")
    public Cart getCartForCurrentUser(Principal principal, @PathVariable String uuid) {
        return cartService.getCartForCurrentUser(principal, uuid);
    }

    @GetMapping("/{uuid}/add/{productId}")
    public void addToCart(Principal principal, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.addItem(principal, uuid, productId);
    }

    @GetMapping("/{uuid}/decrement/{productId}")
    public void decrementItem(Principal principal, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.decrementItem(principal, uuid, productId);
    }

    @GetMapping("/{uuid}/remove/{productId}")
    public void removeItem(Principal principal, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.removeItem(principal, uuid, productId);
    }

    @GetMapping("/clear")
    public void clearCart(Principal principal, @PathVariable String uuid) {
        cartService.clearCart(principal, uuid);
    }
}
