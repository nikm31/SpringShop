package ru.geekbrains.springdata.repositories.role;

import org.springframework.data.repository.CrudRepository;
import ru.geekbrains.springdata.entity.roles.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
	Optional<Role> findByName(String roleName);
}
