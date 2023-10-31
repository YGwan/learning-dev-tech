package org.example.order;

import org.example.product.Product;

public interface OrderPort {

    Product getProductById(Long productId);

    void save(Order order);
}
