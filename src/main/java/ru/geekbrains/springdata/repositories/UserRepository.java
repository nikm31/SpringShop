package ru.geekbrains.springdata.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.geekbrains.springdata.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameAndEmail(String username, String email);
}
