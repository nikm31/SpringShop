package ru.geekbrains.springdata.specifications;


import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.springdata.entity.Product;

public class ProductSpecs {

    private ProductSpecs() {
    }

    public static Specification<Product> titleContains(String word) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + word + "%");
    }

    public static Specification<Product> priceGreaterThanOrEq(double value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), value);
    }

    public static Specification<Product> priceLesserThanOrEq(double value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), value);
    }
}
