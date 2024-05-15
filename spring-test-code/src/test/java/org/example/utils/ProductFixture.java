package org.example.utils;

import org.example.domain.Product;
import org.example.domain.constant.ProductStatus;
import org.example.domain.constant.ProductType;

public class ProductFixture {

    public static Product createProduct(String productNumber, ProductType productType, ProductStatus status, String name, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .productType(productType)
                .productStatus(status)
                .name(name)
                .price(price)
                .build();
    }
}
