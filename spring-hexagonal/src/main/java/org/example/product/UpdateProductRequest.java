package org.example.product;

import lombok.Getter;

@Getter
public class UpdateProductRequest {

    private String name;

    private int price;

    private DiscountPolicy discountPolicy;

    public UpdateProductRequest() {
    }

    public UpdateProductRequest(String name, int price, DiscountPolicy discountPolicy) {
        this.name = name;
        this.price = price;
        this.discountPolicy = discountPolicy;
    }
}
