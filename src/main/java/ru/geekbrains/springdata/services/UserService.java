package ru.geekbrains.springdata.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.springdata.entity.roles.Role;
import ru.geekbrains.springdata.entity.users.User;
import ru.geekbrains.springdata.exceptions.ResourceNotFoundException;
import ru.geekbrains.springdata.repositories.user.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

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

    public void addNewUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ResourceNotFoundException("Имя пользовввателя уже существует");
        }
        user.setSecretAnswer(privateInfoEncoder.encode(user.getSecretAnswer()));
        user.setPassword(privateInfoEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void changeUserPassword(User user) {
        User findUser = userRepository.findByUsernameAndEmail(user.getUsername(), user.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("ведены не корректные данные"));

        if (!privateInfoEncoder.matches(user.getSecretAnswer(), findUser.getSecretAnswer())) {
            throw new ResourceNotFoundException("ведены не корректные данные");
        }

        findUser.setPassword(privateInfoEncoder.encode(user.getPassword()));
    }
}
