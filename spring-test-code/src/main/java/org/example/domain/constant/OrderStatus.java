package org.example.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    INIT("주문생성"),
    CANCELED("주문취소"),
    RECEIVED("주문접수"),
    PAYMENT_COMPLETED("결제 완료"),
    PAYMENT_FAIL("결제 실패"),
    COMPLETED("처리 완료")
    ;

    private final String text;
}
