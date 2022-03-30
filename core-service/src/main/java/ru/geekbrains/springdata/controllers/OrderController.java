package ru.geekbrains.springdata.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springdata.dto.OrderDetailsDto;
import ru.geekbrains.springdata.dto.OrderDto;
import ru.geekbrains.springdata.services.OrderService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody OrderDetailsDto orderDetailsDto, Principal principal) {
        orderService.createOrder(principal, orderDetailsDto);
    }

    @GetMapping
    public List<OrderDto> getOrdersForCurrentUser(Principal principal) {
        return orderService.findAllByUsername(principal.getName()).stream().map(OrderDto::new).collect(Collectors.toList());
    }
}
