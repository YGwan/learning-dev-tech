package org.example.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {

    SELLING("판매중"),
    HOLD("판매 보류"),
    STOP("판매 중지")
    ;

    private final String text;
}
