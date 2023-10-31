package org.example.order;

import org.example.product.ProductFixture;
import org.example.utils.ApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.example.order.OrderFixture.getCreateOrderRequest;
import static org.example.order.OrderFixture.getOrder;

public class OrderApiTest extends ApiTest {

    @Test
    @DisplayName("상품 주문")
    void 상품주문() {
        ProductFixture.registerProduct(ProductFixture.addProductRequest());
        final var request = getCreateOrderRequest();

        final var response = getOrder(request);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }


}
