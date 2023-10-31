package org.example.payment;

public class PaymentFixture {

    public static PaymentRequest paymentRequest() {
        return new PaymentRequest(
                1L, "1234-1234-1234-1234"
        );
    }
}
