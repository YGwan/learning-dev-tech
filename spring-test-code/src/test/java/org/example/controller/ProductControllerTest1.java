package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.constant.ProductStatus;
import org.example.domain.constant.ProductType;
import org.example.dto.request.CreateProductRequest;
import org.example.dto.response.ProductResponse;
import org.example.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest1 {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @DisplayName("신규 상품을 등록한다.")
    @Test
    void createProduct() throws Exception {
        var request = CreateProductRequest.builder()
                .productType(ProductType.HANDMADE)
                .productStatus(ProductStatus.SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        mockMvc.perform(
                        post("/api/v1/products/new")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("상품을 생성할때 요청 형식에 대한 유효성 검사를 진행한다.")
    @Nested
    class requestValidatedTest {

        @DisplayName("신규 상품을 등록할때 상품 타입은 필수적으로 가지고 있어야 된다.")
        @Test
        void createProductWithOutType() throws Exception {
            var requestWithoutType = CreateProductRequest.builder()
                    .productStatus(ProductStatus.SELLING)
                    .name("아메리카노")
                    .price(4000)
                    .build();

            mockMvc.perform(
                            post("/api/v1/products/new")
                                    .content(objectMapper.writeValueAsString(requestWithoutType))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("상품 타입은 필수입니다."))
                    .andExpect(jsonPath("$.data").isEmpty());
        }

        @DisplayName("신규 상품을 등록할때 상품 상태는 필수적으로 가지고 있어야 된다.")
        @Test
        void createProductWithOutStatus() throws Exception {
            var requestWithoutStatus = CreateProductRequest.builder()
                    .productType(ProductType.HANDMADE)
                    .name("아메리카노")
                    .price(4000)
                    .build();

            mockMvc.perform(
                            post("/api/v1/products/new")
                                    .content(objectMapper.writeValueAsString(requestWithoutStatus))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("상품 상태는 필수입니다."))
                    .andExpect(jsonPath("$.data").isEmpty());
        }

        @DisplayName("신규 상품을 등록할때 상품 이름은 필수적으로 가지고 있어야 된다.")
        @Test
        void createProductWithOutName() throws Exception {
            var requestWithoutName = CreateProductRequest.builder()
                    .productType(ProductType.HANDMADE)
                    .productStatus(ProductStatus.SELLING)
                    .price(4000)
                    .build();

            mockMvc.perform(
                            post("/api/v1/products/new")
                                    .content(objectMapper.writeValueAsString(requestWithoutName))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("상품 이름은 필수입니다."))
                    .andExpect(jsonPath("$.data").isEmpty());
        }

        @DisplayName("신규 상품을 등록할때 상품 가격은 양수여야 한다.")
        @Test
        void createProductInappropriatePrice() throws Exception {
            var requestInappropriatePrice = CreateProductRequest.builder()
                    .productType(ProductType.HANDMADE)
                    .productStatus(ProductStatus.SELLING)
                    .name("아메리카노")
                    .price(0)
                    .build();

            mockMvc.perform(
                            post("/api/v1/products/new")
                                    .content(objectMapper.writeValueAsString(requestInappropriatePrice))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("상품 가격이 잘못 입력되었습니다."))
                    .andExpect(jsonPath("$.data").isEmpty());
        }
    }

    @DisplayName("판매 상품을 조회한다.")
    @Test
    void getSellingProducts() throws Exception {
        List<ProductResponse> result = List.of();

        when(productService.getSellingProducts()).thenReturn(result);

        mockMvc.perform(
                        get("/api/v1/products/selling")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }
}
