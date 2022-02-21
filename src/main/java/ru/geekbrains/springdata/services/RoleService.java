package ru.geekbrains.springdata.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.springdata.entity.Role;
import ru.geekbrains.springdata.exceptions.ResourceNotFoundException;
import ru.geekbrains.springdata.repositories.RoleRepository;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
	private final RoleRepository roleRepository;

	public List<Role> getStandardUserRole() {
		return Arrays.asList(roleRepository.findByName("ROLE_USER")
				.orElseThrow(()-> new ResourceNotFoundException("Ни одна стандартная роль не найдена")));
	}
}
