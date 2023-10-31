package org.example.product.application.dto;

import lombok.Getter;
import org.example.product.domain.DiscountPolicy;
import org.example.product.domain.Product;

@Getter
public class GetProductResponse {

    private Long id;

    private String name;

    private int price;

    private DiscountPolicy discountPolicy;

    public GetProductResponse(Long id, String name, int price, DiscountPolicy discountPolicy) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discountPolicy = discountPolicy;
    }

    public static GetProductResponse from(Product product) {
        return new GetProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDiscountPolicy()
        );
    }
}
