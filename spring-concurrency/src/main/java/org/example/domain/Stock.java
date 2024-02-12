package org.example.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
public class Stock {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private Long productId;

    private Long quantity;

    public Stock() {
    }

    public Stock(Long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public void decreaseQuantity(Long quantity) {
        if (this.quantity < quantity) {
            throw new IllegalArgumentException("재고 수량이 없습니다.");
        }

        this.quantity -= quantity;
    }
}
