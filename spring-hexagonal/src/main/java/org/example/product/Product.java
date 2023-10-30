package org.example.product;

import lombok.Getter;

@Getter
public class Product {

    private Long id;

    private String name;

    private int price;

    private DiscountPolicy discountPolicy;

    public Product() {
    }

    public Product(String name, int price, DiscountPolicy discountPolicy) {
        this.name = name;
        this.price = price;
        this.discountPolicy = discountPolicy;
    }

    public void assignId(Long id) {
        this.id = id;
    }

}
