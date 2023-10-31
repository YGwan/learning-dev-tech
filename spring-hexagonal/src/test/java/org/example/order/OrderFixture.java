package org.example.order;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class OrderFixture {

    public static CreateOrderRequest getCreateOrderRequest() {
        final Long productId = 1L;
        final int quantity = 2;

        return new CreateOrderRequest(productId, quantity);
    }

    public static ExtractableResponse<Response> getOrder(CreateOrderRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/orders")
                .then()
                .log().all().extract();
    }
}
