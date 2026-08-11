package com.example.inventory_service.controller;

import com.example.inventory_service.dto.ProductRequest;
import com.example.inventory_service.dto.ProductResponse;
import com.example.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping()
    public ResponseEntity<ProductResponse> createProduct(ProductRequest request){

        ProductResponse response= productService.createProduct(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        List<ProductResponse> response =productService.getAllProducts();

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){

        ProductResponse response= productService.getProductById(id);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(response);

    }
}

