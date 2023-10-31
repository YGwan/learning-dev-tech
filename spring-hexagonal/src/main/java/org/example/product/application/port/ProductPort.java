package org.example.product.application.port;

import org.example.product.domain.Product;

public interface ProductPort {

   void save(Product product);

   Product getProduct(Long productId);
}
