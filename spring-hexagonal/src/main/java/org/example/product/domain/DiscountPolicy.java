package org.example.product.domain;

public enum DiscountPolicy {
    NONE {
        @Override
        int applyDiscount(int price) {
            return price;
        }
    };

    abstract int applyDiscount(int price);
}
