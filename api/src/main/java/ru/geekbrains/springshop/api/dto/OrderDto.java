package ru.geekbrains.springshop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Заказ")
public class OrderDto {

    @Schema(description = "ID заказа", required = true, example = "1")
    private Long id;

    @Schema(description = "Список продуктов")
    private List<OrderItemDto> items;

    @Schema(description = "Адрес доставки", required = true, example = "Москва, ул. Ленина, д.1б, кв. 1")
    private String address;

    @Schema(description = "Номер телефона для связи", required = true, example = "89050809353")
    private String phone;

    @Schema(description = "Общая стоимость за все продукты в заказе", required = true, example = "50000.00")
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
