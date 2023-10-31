package org.example.payment.application.dto;

import lombok.Getter;

@Getter
public class PaymentRequest {

    private Long orderId;

    private  String cardNumber;

    public PaymentRequest() {
    }

    public PaymentRequest(Long orderId, String cardNumber) {
        this.orderId = orderId;
        this.cardNumber = cardNumber;
    }
}
