package org.example.utils;

import org.example.product.AddProductRequest;
import org.example.product.DiscountPolicy;

public class AddProductRequestFixture {

    public static AddProductRequest get() {
        return new AddProductRequest(
                "name",
                1000,
                DiscountPolicy.NONE
        );
    }
}
