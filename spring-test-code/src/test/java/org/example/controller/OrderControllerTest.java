package org.example.controller;

import org.example.dto.request.OrderCreateRequest;
import org.example.integration.ControllerTestContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends ControllerTestContext {

    @DisplayName("상품 주문을 등록한다.")
    @Test
    void createOrder() throws Exception {
        List<String> productNumbers = List.of("001", "002");
        var request = OrderCreateRequest.builder()
                .productNumbers(productNumbers)
                .build();

        mockMvc.perform(
                        post("/api/v1/orders/new")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("주문을 등록할때 상품 번호는 한개 이상이여야 한다.")
    @Test
    void createOrderWithEmptyProductNumbers() throws Exception {
        List<String> productNumbers = List.of();

        var request = OrderCreateRequest.builder()
                .productNumbers(productNumbers)
                .build();

        mockMvc.perform(
                        post("/api/v1/orders/new")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("상품 번호 리스트는 필수입니다."));
    }
}