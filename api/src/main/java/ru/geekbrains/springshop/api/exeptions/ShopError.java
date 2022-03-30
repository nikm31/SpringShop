package ru.geekbrains.springshop.api.exeptions;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    public ShopError(List<String> messages, Date date) {
        this.messages = messages;
        this.date = date;
    }

    public ShopError() {
    }
}
