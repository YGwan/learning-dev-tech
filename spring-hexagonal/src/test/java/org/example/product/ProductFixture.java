package org.example.product;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

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

    public static ExtractableResponse<Response> registerProduct(AddProductRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/products")
                .then()
                .log().all().extract();
    }
}
