package org.example;

import org.example.product.AddProductRequest;
import org.example.product.DiscountPolicy;
import org.example.product.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("상품 등록")
    void 상품등록() {
        final AddProductRequest request = getRequest();

        productService.addProduct(request);
    }

    private AddProductRequest getRequest() {
        return new AddProductRequest(
                "name",
                1000,
                DiscountPolicy.NONE
        );
    }
}