package org.example.order;

public class OrderFixture {

    public static CreateOrderRequest getCreateOrderRequest() {
        final Long productId = 1L;
        final int quantity = 2;

        return new CreateOrderRequest(productId, quantity);
    }
}
