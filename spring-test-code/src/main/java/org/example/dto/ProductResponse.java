package org.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.Product;
import org.example.domain.constant.ProductStatus;
import org.example.domain.constant.ProductType;

@NoArgsConstructor
@Getter
public class ProductResponse {

    private Long id;

    private String productNumber;

    private ProductType productType;

    private ProductStatus productStatus;

    private String name;

    private int price;

    @Builder
    private ProductResponse(Long id, String productNumber, ProductType productType,
        ProductStatus productStatus,
        String name, int price) {
        this.id = id;
        this.productNumber = productNumber;
        this.productType = productType;
        this.productStatus = productStatus;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
            .id(product.getId())
            .productNumber(product.getProductNumber())
            .productType(product.getProductType())
            .productStatus(product.getProductStatus())
            .name(product.getName())
            .price(product.getPrice())
            .build();
    }
}
