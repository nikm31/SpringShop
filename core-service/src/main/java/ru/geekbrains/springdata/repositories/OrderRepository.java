package ru.geekbrains.springdata.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.springdata.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
//    @Query("select o from Order o where o.user.username = :username")
//    @EntityGraph(value = "orders.for-front")
//    List<Order> findAllByUsername(String username);
}
