package ru.geekbrains.springdata.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ShopError {
    private List<String> messages;
    private Date date;

    public ShopError(List<String> messages) {
        this.messages = messages;
        this.date = new Date();
    }

    public ShopError(String message) {
        this(List.of(message));
    }

    public ShopError(String... messages) {
        this(Arrays.asList(messages));
    }
}
