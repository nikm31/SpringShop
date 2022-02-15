package ru.geekbrains.springdata.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/")
public class MainController {
    @GetMapping("/")
    public void showHomePage(HttpServletRequest request, HttpServletResponse response) {
    }
}
