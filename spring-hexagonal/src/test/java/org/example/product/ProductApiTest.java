package org.example.product;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.example.utils.ApiTest;
import org.example.utils.ProductFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

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
        final var request = ProductFixture.addProductRequest();
        //API 요청
        final var response = 상품등록(request);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    private static ExtractableResponse<Response> 상품등록(AddProductRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/products")
                .then()
                .log().all().extract();
    }

    @Test
    @DisplayName("상품 조회")
    void 상품_조회() {
        상품등록(ProductFixture.addProductRequest());

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
        상품등록(ProductFixture.addProductRequest());

        final var response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(ProductFixture.updateProductRequest())
                .when()
                .put("/products/{productId}", 1)
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(productRepository.findById(1L).get().getName()).isEqualTo("상품 수정");
    }
}
