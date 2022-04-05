package ru.geekbrains.springdata.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springdata.dto.OrderDetailsDto;
import ru.geekbrains.springdata.dto.OrderDto;
import ru.geekbrains.springdata.services.OrderService;
import ru.geekbrains.springshop.api.dto.PageDto;
import ru.geekbrains.springshop.api.exeptions.ShopError;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Страница заказов", description = "Отображение заказов для зарегистрированного пользователя")
public class OrderController {
    private final OrderService orderService;

    @Operation(
            summary = "Создание заказа",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody OrderDetailsDto orderDetailsDto, Principal principal) {
        orderService.createOrder(principal, orderDetailsDto);
    }

    @Operation(
            summary = "Создание заказа",
            responses = {
                    @ApiResponse(
                            description = "Список заказов", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = List.class))
                    )
            }
    )
    @GetMapping
    public List<OrderDto> getOrdersForCurrentUser(Principal principal) {
        return orderService.findAllByUsername(principal.getName()).stream().map(OrderDto::new).collect(Collectors.toList());
    }
}
