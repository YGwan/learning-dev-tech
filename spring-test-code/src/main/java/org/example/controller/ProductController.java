package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.request.CreateProductRequest;
import org.example.dto.response.ApiResponse;
import org.example.dto.response.ProductResponse;
import org.example.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping("/selling")
    public ApiResponse<List<ProductResponse>> getSellingProducts() {
        List<ProductResponse> products = productService.getSellingProducts();
        return ApiResponse.of(HttpStatus.OK, products);
    }

    @PostMapping("/new")
    public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ApiResponse.success(response);
    }
}
