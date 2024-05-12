package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.Order;
import org.example.domain.constant.OrderStatus;
import org.example.repository.OrderRepository;
import org.example.service.mail.MailService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderStatisticsService {

    private final OrderRepository orderRepository;
    private final MailService mailService;

    public void sendOrderStatisticsMail(LocalDate orderDate, String email) {
        List<Order> orders = orderRepository.findOrdersBy(
                orderDate.atStartOfDay(),
                orderDate.plusDays(1).atStartOfDay(),
                OrderStatus.PAYMENT_COMPLETED
        );

        int totalAmount = orders.stream()
                .mapToInt(Order::getTotalPrice)
                .sum();

        boolean result = mailService.sendMail(email, "[매출 통계] - " + orderDate, "총 매출 합계 : " + totalAmount);

        if (!result) {
            throw new IllegalArgumentException("매출 통계 메일 전송 실패");
        }
    }
}
