package ru.geekbrains.springdata.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.springdata.dto.UserDto;
import ru.geekbrains.springdata.entity.Role;
import ru.geekbrains.springdata.entity.User;
import ru.geekbrains.springdata.exceptions.ResourceNotFoundException;
import ru.geekbrains.springdata.repositories.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    private BCryptPasswordEncoder privateInfoEncoder;

    @Autowired
    public void setPrivateInfoEncoder(BCryptPasswordEncoder privateInfoEncoder) {
        this.privateInfoEncoder = privateInfoEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public ResponseEntity<String> registerNewUser(UserDto userDto) {

        if (!userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            return ResponseEntity.badRequest().body("Пароли не совпадают");
        }
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Имя пользовввателя уже существует: " + userDto.getUsername());
        }
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Такой Email уже занят: " + userDto.getEmail());
        }

        User user = new User();
        user.setRoles(roleService.getStandardUserRole());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setSecretQuestion(userDto.getSecretQuestion());
        user.setSecretAnswer(privateInfoEncoder.encode(userDto.getSecretAnswer()));
        user.setPassword(privateInfoEncoder.encode(userDto.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok("Успешная регистрация");
    }

    @Transactional
    public ResponseEntity<String> changeUserPassword(UserDto inputUser) {
        User currentUser = userRepository.findByUsernameAndEmail(inputUser.getUsername(), inputUser.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с таким Email: " + inputUser.getEmail()
                        + " и логином: " + inputUser.getUsername() + " не найден"));

        if (!privateInfoEncoder.matches(inputUser.getSecretAnswer(), currentUser.getSecretAnswer())) {
            throw new ResourceNotFoundException("Ответ на секретный вопрос: " + currentUser.getSecretQuestion() + " не верный");
        }

        currentUser.setPassword(privateInfoEncoder.encode(inputUser.getPassword()));
        return ResponseEntity.ok("Пароль успешно сменен");
    }
}
