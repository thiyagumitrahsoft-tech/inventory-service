package com.example.inventory_service.service;

import com.example.inventory_service.dto.InventoryResponse;
import com.example.inventory_service.dto.StockUpdateRequest;

public interface InventoryService {

    InventoryResponse getInventoryByProductId(Long productId);

    InventoryResponse updateStock(Long productId, StockUpdateRequest request);
}
