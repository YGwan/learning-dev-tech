package org.example.payment;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.example.order.OrderFixture;
import org.example.product.HexProductFixture;
import org.example.utils.ApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PaymentApiTest extends ApiTest {

    @Test
    void 상품_주문() {
        HexProductFixture.registerProduct(HexProductFixture.addProductRequest());
        OrderFixture.getOrder(OrderFixture.getCreateOrderRequest());

        final var request = PaymentFixture.paymentRequest();

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/payments")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
