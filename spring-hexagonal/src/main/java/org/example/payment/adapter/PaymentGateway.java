package org.example.payment.adapter;

public interface PaymentGateway {

    void execute(int totalPrice, String cardNumber);
}
