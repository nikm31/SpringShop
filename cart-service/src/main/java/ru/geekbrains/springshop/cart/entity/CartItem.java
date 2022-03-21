package ru.geekbrains.springshop.cart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.springshop.api.dto.ProductDto;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Long productId;
    private String productTitle;
    private int quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal price;
    private String mainImagePath;

    public CartItem(ProductDto product) {
        this.productId = product.getId();
        this.productTitle = product.getTitle();
        this.quantity = 1;
        this.price = product.getPrice();
        this.pricePerProduct = product.getPrice();
        this.mainImagePath = product.getMainImagePath();
    }

    public void changeQuantity(int delta) {
        quantity += delta;
        if (quantity < 0) {
            quantity = 0;
        }
        price = pricePerProduct.multiply(BigDecimal.valueOf(quantity));
    }
}
