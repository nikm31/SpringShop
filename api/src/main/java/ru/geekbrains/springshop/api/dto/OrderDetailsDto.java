package ru.geekbrains.springshop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Детали для заказа")
public class OrderDetailsDto {

    @Schema(description = "Номер телефона для связи", required = true, example = "89050809353")
    private String phone;

    @Schema(description = "Адрес доставки", required = true, example = "Москва, ул. Ленина, д.1б, кв. 1")
    private String address;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
