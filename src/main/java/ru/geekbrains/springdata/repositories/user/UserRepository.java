package ru.geekbrains.springdata.repositories.user;

import org.springframework.data.repository.CrudRepository;
import ru.geekbrains.springdata.entity.users.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndEmail(String username, String email);
}
