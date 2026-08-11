package com.example.inventory_service.service.serviceImpl;


import com.example.inventory_service.config.MapperConfig;
import com.example.inventory_service.dto.ProductRequest;
import com.example.inventory_service.dto.ProductResponse;
import com.example.inventory_service.entity.Product;
import com.example.inventory_service.repository.ProductRepository;
import com.example.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;

    private final ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductRequest request) {

        if (request.getSku() != null &&
                productRepository.existsBySku(request.getSku())) {

            throw new RuntimeException(
                    "Product already exists with SKU: "
                            + request.getSku()
            );
        }

        Product product = modelMapper.map(request, Product.class);

        Product savedProduct =productRepository.save(product);

        return modelMapper.map(savedProduct, ProductResponse.class);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<ProductResponse> response= productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product,ProductResponse.class))
                .toList();

        return response;
    }

    @Override
    public ProductResponse getProductById(Long id) {

        Product product= productRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Product not available"));

        ProductResponse response = modelMapper.map(product, ProductResponse.class);

        return response;
    }
}