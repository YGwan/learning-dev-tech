package org.example.order;

import io.restassured.RestAssured;
import org.example.product.ProductFixture;
import org.example.utils.ApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class OrderApiTest extends ApiTest {

    @Test
    @DisplayName("상품 주문")
    void 상품주문() {
        ProductFixture.registerProduct(ProductFixture.addProductRequest());
        final var request = OrderFixture.getCreateOrderRequest();

        final var response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/orders")
                .then()
                .log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }
}
