package org.example.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.base.BaseEntity;
import org.example.domain.constant.ProductStatus;
import org.example.domain.constant.ProductType;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productNumber;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    private String name;

    private int price;

    @Builder
    private Product(String productNumber, ProductType productType, ProductStatus productStatus, String name, int price) {
        this.productNumber = productNumber;
        this.productType = productType;
        this.productStatus = productStatus;
        this.name = name;
        this.price = price;
    }
}
