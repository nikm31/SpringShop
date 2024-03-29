package ru.geekbrains.springdata.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.springdata.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends PagingAndSortingRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("SELECT MAX(price) FROM Product")
    Double findMaxPrice();

    Optional<Product> findByTitle(String title);

    List<Product> findAll();
}
