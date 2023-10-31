package org.example.payment;

public interface PaymentGateway {

    void execute(int totalPrice, String cardNumber);
}
