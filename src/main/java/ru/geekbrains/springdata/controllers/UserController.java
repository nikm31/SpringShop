package ru.geekbrains.springdata.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springdata.dto.UserDto;
import ru.geekbrains.springdata.services.UserService;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto user) {
        return userService.registerNewUser(user);
    }

    @PutMapping("recover_password")
    public ResponseEntity<String> changeUserPassword(@RequestBody UserDto user) {
        return userService.changeUserPassword(user);
    }

    @GetMapping("account")
    public UserDetails loadUserByUsername (@RequestParam(value = "username") String username) {
        return userService.loadUserByUsername(username);
    }
}
