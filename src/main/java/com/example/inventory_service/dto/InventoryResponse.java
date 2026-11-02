package com.example.inventory_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryResponse {

    private Long productId;
    private Integer availableQuantity;
}
