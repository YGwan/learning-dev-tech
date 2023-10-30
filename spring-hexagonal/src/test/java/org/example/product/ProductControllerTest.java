package org.example.product;

import org.example.utils.AddProductRequestFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @Test
    @DisplayName("상품 등록")
    void 상품등록() {
        final AddProductRequest request = AddProductRequestFixture.get();
        productController.addProduct(request);
    }
}