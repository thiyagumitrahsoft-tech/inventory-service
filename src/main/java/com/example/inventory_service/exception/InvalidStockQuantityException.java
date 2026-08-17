package com.example.inventory_service.exception;

public class InvalidStockQuantityException extends RuntimeException {

    public InvalidStockQuantityException(String message) {
        super(message);
    }
}
