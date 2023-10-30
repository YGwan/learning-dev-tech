package org.example.product;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.example.utils.AddProductRequestFixture;
import org.example.utils.ApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductApiTest extends ApiTest {

    @Test
    @DisplayName("상품 등록")
    void 상품_등록() {
        final var request = AddProductRequestFixture.get();
        //API 요청
        final var response = 상품등록(request);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("상품 조회")
    void 상품_조회() {
        상품등록(AddProductRequestFixture.get());

        final Long productId = 1L;

        final var response = RestAssured.given().log().all()
                .get("/products/{productId}", productId)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("name")).isEqualTo("name");
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
}
