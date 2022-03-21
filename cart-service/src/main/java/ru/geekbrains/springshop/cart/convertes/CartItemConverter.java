package ru.geekbrains.springshop.cart.convertes;

import org.springframework.stereotype.Component;
import ru.geekbrains.springshop.api.dto.CartItemDto;
import ru.geekbrains.springshop.cart.entity.CartItem;

@Component
public class CartItemConverter {
    public CartItemDto entityToDto(CartItem cartItem) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setPrice(cartItem.getPrice());
        cartItemDto.setPricePerProduct(cartItem.getPricePerProduct());
        cartItemDto.setQuantity(cartItem.getQuantity());
        cartItemDto.setProductTitle(cartItem.getProductTitle());
        cartItemDto.setProductId(cartItem.getProductId());
        return cartItemDto;
    }
}
