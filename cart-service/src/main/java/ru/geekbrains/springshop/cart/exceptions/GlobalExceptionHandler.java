package ru.geekbrains.springshop.cart.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.geekbrains.springshop.api.exeptions.DataValidationException;
import ru.geekbrains.springshop.api.exeptions.ResourceNotFoundException;
import ru.geekbrains.springshop.api.exeptions.ShopError;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> catchResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new ShopError(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> catchValidationException(DataValidationException e) {
        return new ResponseEntity<>(new ShopError(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
