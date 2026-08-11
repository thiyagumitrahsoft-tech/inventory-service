package com.example.inventory_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockUpdateRequest {

    @NotNull(message = "Available quantity is required")
    @Min(value = 0, message = "Available quantity must not be negative")
    private Integer availableQuantity;
}
