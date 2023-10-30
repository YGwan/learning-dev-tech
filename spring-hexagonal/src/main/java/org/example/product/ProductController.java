package org.example.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductPort productPort;

    public ProductController(ProductPort productPort) {
        this.productPort = productPort;
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestBody AddProductRequest request) {
        final Product product = new Product(request.getName(), request.getPrice(), request.getDiscountPolicy());
        productPort.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
