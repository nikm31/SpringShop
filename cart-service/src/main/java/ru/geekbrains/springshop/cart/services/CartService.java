package ru.geekbrains.springshop.cart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.geekbrains.springshop.api.dto.ProductDto;
import ru.geekbrains.springshop.cart.entity.Cart;
import ru.geekbrains.springshop.cart.integrations.ProductServiceIntegration;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductServiceIntegration productService;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String REDIS_CART_PREFIX = "MY_MARKET_CART_";

    private String getCartKey(Principal principal, String uuid) {
        if (principal != null) {
            return REDIS_CART_PREFIX + principal.getName();
        }
        return REDIS_CART_PREFIX + uuid;
    }

    public Cart getCartForCurrentUser(Principal principal, String uuid) {
        String cartKey = getCartKey(principal, uuid);
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }
        Cart cart = (Cart) redisTemplate.opsForValue().get(cartKey);
        return cart;
    }

    public Cart getCartByKey(String key) {
        if (!redisTemplate.hasKey(REDIS_CART_PREFIX + key)) {
            redisTemplate.opsForValue().set(REDIS_CART_PREFIX + key, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(REDIS_CART_PREFIX + key);
    }

    public void updateCart(Principal principal, String uuid, Cart cart) {
        String cartKey = getCartKey(principal, uuid);
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    public void updateCartByKey(String key, Cart cart) {
        redisTemplate.opsForValue().set(REDIS_CART_PREFIX + key, cart);
    }

    public void addItem(Principal principal, String uuid, Long productId) {
        Cart cart = getCartForCurrentUser(principal, uuid);
        if (cart.add(productId)) {
            updateCart(principal, uuid, cart);
            return;
        }
        ProductDto product = productService.getProductById(productId);
        cart.add(product);
        updateCart(principal, uuid, cart);
    }

    public void removeItem(Principal principal, String uuid, Long productId) {
        Cart cart = getCartForCurrentUser(principal, uuid);
        cart.remove(productId);
        updateCart(principal, uuid, cart);
    }

    public void decrementItem(Principal principal, String uuid, Long productId) {
        Cart cart = getCartForCurrentUser(principal, uuid);
        cart.decrement(productId);
        updateCart(principal, uuid, cart);
    }

    public void clearCart(Principal principal, String uuid) {
        Cart cart = getCartForCurrentUser(principal, uuid);
        cart.clear();
        updateCart(principal, uuid, cart);
    }

    public void merge(Principal principal, String uuid) {
        Cart guestCart = getCartByKey(uuid);
        Cart userCart = getCartByKey(principal.getName());
        userCart.merge(guestCart);
        updateCartByKey(principal.getName(), userCart);
        updateCartByKey(uuid, guestCart);
    }
}