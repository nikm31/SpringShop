package ru.geekbrains.springdata.entity.shop;

import ru.geekbrains.springdata.dto.ProductDto;


public class Products {
    private ProductDto[] products;

    public ProductDto[] getProducts() {
        return products;
    }

    public void setProducts(ProductDto[] products) {
        this.products = products;
    }
}
