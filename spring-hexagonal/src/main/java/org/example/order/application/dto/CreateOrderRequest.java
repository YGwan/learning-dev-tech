package org.example.order.application.dto;

import lombok.Getter;

@Getter
public class CreateOrderRequest {

    private Long productId;

    private int quantity;

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
