package com.example.inventory_service.service.serviceImpl;

import com.example.inventory_service.dto.InventoryResponse;
import com.example.inventory_service.dto.StockUpdateRequest;
import com.example.inventory_service.entity.Inventory;
import com.example.inventory_service.repository.InventoryRepository;
import com.example.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    @Cacheable(cacheNames = "inventory", key = "#productId")
    public InventoryResponse getInventoryByProductId(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException(
                        "Inventory not found for product id: " + productId
                ));
        return toResponse(inventory);
    }

    @Override
    @CacheEvict(cacheNames = "inventory", key = "#productId")
    public InventoryResponse updateStock(Long productId, StockUpdateRequest request) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException(
                        "Inventory not found for product id: " + productId
                ));
        inventory.setAvailableQuantity(request.getAvailableQuantity());

        Inventory savedInventory = inventoryRepository.save(inventory);
        InventoryResponse response = toResponse(savedInventory);

        return response;
    }

    private InventoryResponse toResponse(Inventory inventory) {
        InventoryResponse response = new InventoryResponse();
        response.setProductId(inventory.getProductId());
        response.setAvailableQuantity(inventory.getAvailableQuantity());
        return response;
    }
}
