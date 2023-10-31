package org.example.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductService {

    private final ProductPort productPort;

    public ProductService(ProductPort productPort) {
        this.productPort = productPort;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> addProduct(@RequestBody AddProductRequest request) {
        final var product = new Product(request.getName(), request.getPrice(), request.getDiscountPolicy());
        productPort.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("{productId}")
    public ResponseEntity<GetProductResponse> getProduct(@PathVariable Long productId) {
        final var product = productPort.getProduct(productId);
        final var response = GetProductResponse.from(product);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{productId}")
    @Transactional
    public ResponseEntity<Void> updateProduct(@PathVariable Long productId, @RequestBody UpdateProductRequest request) {
        final var product = productPort.getProduct(productId);
        product.update(request);
        return ResponseEntity.ok().build();
    }
}
