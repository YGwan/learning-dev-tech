package org.example.payment.application.service;

import org.example.order.Order;
import org.example.payment.application.port.PaymentPort;
import org.example.payment.application.dto.PaymentRequest;
import org.example.payment.domain.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentService {

    private PaymentPort paymentPort;

    public PaymentService(PaymentPort paymentPort) {
        this.paymentPort = paymentPort;
    }

    @PostMapping
    public ResponseEntity<Void> payment(@RequestBody PaymentRequest request) {
        final  Order order = paymentPort.getOrder(request.getOrderId());
        final Payment payment = new Payment(order, request.getCardNumber());

        paymentPort.pay(payment.getPrice(), payment.getCardNumber());
        paymentPort.save(payment);

        return ResponseEntity.ok().build();
    }
}
