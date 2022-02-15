package ru.geekbrains.springdata.repositories.role;

import org.springframework.data.repository.CrudRepository;
import ru.geekbrains.springdata.entity.roles.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
