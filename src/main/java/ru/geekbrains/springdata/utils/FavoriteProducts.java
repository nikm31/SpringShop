package ru.geekbrains.springdata.utils;

import lombok.Data;
import ru.geekbrains.springdata.dto.ProductDto;
import java.util.*;

@Data
public class FavoriteProducts {
    private Map<Long, ProductDto> items;

    public FavoriteProducts() {
        this.items = new HashMap<>();
    }

    public void add(ProductDto product) {
        items.putIfAbsent(product.getId(), product);
    }

    public void remove(Long productId) {
        items.remove(productId);
    }

    public void clear() {
        items.clear();
    }
}