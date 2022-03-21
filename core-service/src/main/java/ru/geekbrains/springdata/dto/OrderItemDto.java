package ru.geekbrains.springdata.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.springdata.entity.OrderItem;
import ru.geekbrains.springdata.entity.Product;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderItemDto {
    private Long productId;
    private String productTitle;
    private int quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal price;
    private String mainImagePath;

    public OrderItemDto(Product product) {
        this.productId = product.getId();
        this.productTitle = product.getTitle();
        this.quantity = 1;
        this.price = product.getPrice();
        this.pricePerProduct = product.getPrice();
        this.mainImagePath = product.getMainImagePath();
    }

    public OrderItemDto(OrderItem orderItem) {
        this.productId = orderItem.getProduct().getId();
        this.productTitle = orderItem.getProduct().getTitle();
        this.quantity = orderItem.getQuantity();
        this.price = orderItem.getPrice();
        this.pricePerProduct = orderItem.getPricePerProduct();
    }

    public void changeQuantity(int delta) {
        quantity += delta;
        if (quantity < 0) {
            quantity = 0;
        }
        price = pricePerProduct.multiply(BigDecimal.valueOf(quantity));
    }
}
