package ru.geekbrains.springdata.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.springdata.entity.roles.Role;
import ru.geekbrains.springdata.exceptions.ResourceNotFoundException;
import ru.geekbrains.springdata.repositories.role.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
	private final RoleRepository roleRepository;

	public Role getStandardUserRole() {
		return roleRepository.findByName("ROLE_USER")
				.orElseThrow(() -> new ResourceNotFoundException("Стандартная роль не найдена"));
	}
}
