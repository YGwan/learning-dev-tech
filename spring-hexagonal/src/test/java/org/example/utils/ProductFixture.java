package org.example.utils;

import org.example.product.AddProductRequest;
import org.example.product.DiscountPolicy;
import org.example.product.Product;
import org.example.product.UpdateProductRequest;

public class ProductFixture {

    public static Product product() {
        return new Product(
                "상품명", 1000, DiscountPolicy.NONE
        );
    }

    public static AddProductRequest addProductRequest() {
        return new AddProductRequest(
                "name",
                1000,
                DiscountPolicy.NONE
        );
    }

    public static UpdateProductRequest updateProductRequest() {
        return new UpdateProductRequest(
                "상품 수정", 2000, DiscountPolicy.NONE
        );
    }
}
