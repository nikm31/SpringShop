package ru.geekbrains.springdata.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.springdata.dto.OrderDetailsDto;
import ru.geekbrains.springdata.entity.Order;
import ru.geekbrains.springdata.entity.OrderItem;
import ru.geekbrains.springdata.integrations.AuthServiceIntegration;
import ru.geekbrains.springdata.integrations.CartServiceIntegration;
import ru.geekbrains.springdata.repositories.OrderRepository;
import ru.geekbrains.springshop.api.dto.CartDto;
import ru.geekbrains.springshop.api.dto.CartItemDto;
import ru.geekbrains.springshop.api.dto.UserDto;
import ru.geekbrains.springshop.api.exeptions.ResourceNotFoundException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final AuthServiceIntegration userService;
    private final CartServiceIntegration cartService;
    private final ProductService productService;

    @Transactional
    public void createOrder(Principal principal, OrderDetailsDto orderDetailsDto) {
        UserDto user = userService.findByUsername(principal.getName());
//        CartDto cart = cartService.getCurrentCart(principal, null);
        CartDto cart = null;
        Order order = new Order();
        order.setUser(user.getUsername());
        order.setPrice(cart.getTotalPrice());
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        List<OrderItem> items = new ArrayList<>();
        for (CartItemDto i : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setPrice(i.getPrice());
            orderItem.setPricePerProduct(i.getPricePerProduct());
            orderItem.setQuantity(i.getQuantity());
            orderItem.setProduct(productService.findById(i.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Не удалось найти продукт при оформлении заказа. ID продукта: " + i.getProductId())));
            items.add(orderItem);
        }
        order.setItems(items);
        orderRepository.save(order);
//        cartService.clearCart(principal, null);
    }

    public List<Order> findAllByUsername(String username) {
//        return orderRepository.findAllByUsername(username);
        return null;
    }
}
