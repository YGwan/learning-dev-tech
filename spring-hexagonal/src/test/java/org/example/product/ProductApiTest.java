package org.example.product;

import io.restassured.RestAssured;
import org.example.product.adapter.ProductRepository;
import org.example.product.application.port.ProductPort;
import org.example.product.application.service.ProductService;
import org.example.utils.ApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.product.HexProductFixture.*;

public class ProductApiTest extends ApiTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductPort productPort;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 등록")
    void 상품_등록() {
        final var request = addProductRequest();
        //API 요청
        final var response = registerProduct(request);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("상품 조회")
    void 상품_조회() {
        registerProduct(addProductRequest());

        final Long productId = 1L;

        final var response = RestAssured.given().log().all()
                .get("/products/{productId}", productId)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("name")).isEqualTo("name");
    }

    @Test
    void 상품_수정() {
        registerProduct(addProductRequest());

        final var response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updateProductRequest())
                .when()
                .put("/products/{productId}", 1)
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(productRepository.findById(1L).get().getName()).isEqualTo("상품 수정");
    }
}
