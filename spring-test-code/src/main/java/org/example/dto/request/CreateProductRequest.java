package org.example.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.Product;
import org.example.domain.constant.ProductStatus;
import org.example.domain.constant.ProductType;

@NoArgsConstructor
@Getter
public class CreateProductRequest {

    private ProductType productType;
    private ProductStatus productStatus;
    private String name;
    private int price;

    @Builder
    private CreateProductRequest(ProductType productType, ProductStatus productStatus, String name, int price) {
        this.productType = productType;
        this.productStatus = productStatus;
        this.name = name;
        this.price = price;
    }

    public Product toEntity(String nextProductNumber) {
        return Product.builder()
                .productNumber(nextProductNumber)
                .productType(productType)
                .productStatus(productStatus)
                .name(name)
                .price(price)
                .build();
    }
}
