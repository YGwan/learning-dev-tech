package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.request.CreateProductRequest;
import org.example.dto.response.ProductResponse;
import org.example.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping("/selling")
    public List<ProductResponse> getSellingProducts() {
        return productService.getSellingProducts();
    }

    @PostMapping("/new")
    public ProductResponse createProduct(@RequestBody CreateProductRequest request) {
        return productService.createProduct(request);
    }
}
