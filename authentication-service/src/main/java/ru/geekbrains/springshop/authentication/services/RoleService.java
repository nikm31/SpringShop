package ru.geekbrains.springshop.authentication.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.geekbrains.springshop.api.exeptions.ResourceNotFoundException;
import ru.geekbrains.springshop.authentication.entity.Role;
import ru.geekbrains.springshop.authentication.entity.User;
import ru.geekbrains.springshop.authentication.repositories.RoleRepository;
import ru.geekbrains.springshop.authentication.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService implements UserDetailsService {
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username )));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	public List<Role> getStandardUserRole() {
		return Arrays.asList(roleRepository.findByName("ROLE_USER")
				.orElseThrow(()-> new ResourceNotFoundException("Ни одна стандартная роль не найдена")));
	}
}
