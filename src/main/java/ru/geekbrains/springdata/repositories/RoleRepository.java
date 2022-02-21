package ru.geekbrains.springdata.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.geekbrains.springdata.entity.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
	Optional<Role> findByName(String role);
}
