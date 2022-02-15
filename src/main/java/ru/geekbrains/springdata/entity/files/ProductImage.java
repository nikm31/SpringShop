package ru.geekbrains.springdata.entity.files;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.springdata.entity.shop.Product;

import javax.persistence.*;

@Entity
@Table(name = "products_images")
@Data
@NoArgsConstructor
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "path")
    private String path;
}