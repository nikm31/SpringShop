package ru.geekbrains.springdata.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.springdata.dto.OrderDetailsDto;
import ru.geekbrains.springdata.dto.OrderItemDto;
import ru.geekbrains.springdata.entity.Order;
import ru.geekbrains.springdata.entity.OrderItem;
import ru.geekbrains.springdata.entity.User;
import ru.geekbrains.springdata.exceptions.ResourceNotFoundException;
import ru.geekbrains.springdata.repositories.OrderRepository;
import ru.geekbrains.springdata.utils.Cart;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;
    private final ProductService productService;

    @Transactional
    public void createOrder(Principal principal, OrderDetailsDto orderDetailsDto) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("Не удалось найти пользователя при оформлении заказа. Имя пользователя: " + principal.getName()));
        Cart cart = cartService.getCartForCurrentUser(principal, null);
        Order order = new Order();
        order.setUser(user);
        order.setPrice(cart.getTotalPrice());
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        List<OrderItem> items = new ArrayList<>();
        for (OrderItemDto i : cart.getItems()) {
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
        cartService.clearCart(principal, null);
    }

    public List<Order> findAllByUsername(String username) {
        return orderRepository.findAllByUsername(username);
    }
}
