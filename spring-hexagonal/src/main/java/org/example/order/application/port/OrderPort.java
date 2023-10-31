package org.example.order.application.port;

import org.example.order.domain.Order;
import org.example.product.domain.Product;

public interface OrderPort {

    Product getProductById(Long productId);

    void save(Order order);
}
