package org.example.product.application.dto;

import lombok.Getter;
import org.example.product.domain.DiscountPolicy;

@Getter
public class AddProductRequest {

    private String name;

    private int price;

    private DiscountPolicy discountPolicy;

    public AddProductRequest() {
    }

    public AddProductRequest(String name, int price, DiscountPolicy discountPolicy) {
        this.name = name;
        this.price = price;
        this.discountPolicy = discountPolicy;
    }
}
