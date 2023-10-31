package org.example.order;

import org.example.product.domain.Product;

public interface OrderPort {

    Product getProductById(Long productId);

    void save(Order order);
}
