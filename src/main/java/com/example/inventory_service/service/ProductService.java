package com.example.inventory_service.service;


import com.example.inventory_service.dto.ProductRequest;
import com.example.inventory_service.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);
}