package org.example.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.Product;
import org.example.domain.constant.ProductStatus;
import org.example.domain.constant.ProductType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@Getter
public class CreateProductRequest {

    @NotNull
    private ProductType productType;

    @NotNull
    private ProductStatus productStatus;

    @NotBlank
    private String name;

    @Positive
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
