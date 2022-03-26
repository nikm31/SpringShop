package ru.geekbrains.springshop.filestorage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.springshop.filestorage.entity.ProductImage;

import java.util.List;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProductId(Long id);

}

