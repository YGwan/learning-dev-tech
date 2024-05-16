package org.example.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.OrderController;
import org.example.controller.ProductController;
import org.example.service.OrderService;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        ProductController.class,
        OrderController.class
})
public class ControllerTestContext {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected ProductService productService;

    @MockBean
    protected OrderService orderService;
}
