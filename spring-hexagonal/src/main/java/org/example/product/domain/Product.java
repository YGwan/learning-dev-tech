package org.example.product.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.product.application.dto.UpdateProductRequest;

import javax.persistence.*;

@Entity
@Table (name = "product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    private DiscountPolicy discountPolicy;

    public Product(String name, int price, DiscountPolicy discountPolicy) {
        this.name = name;
        this.price = price;
        this.discountPolicy = discountPolicy;
    }

    public void update(UpdateProductRequest request) {
        this.name = request.getName();
        this.price = request.getPrice();
        this.discountPolicy = request.getDiscountPolicy();
    }

    public int getDiscountedPrice() {
        return discountPolicy.applyDiscount(price);
    }
}
