package org.example.product;

public interface ProductPort {

   void save(Product product);

   Product getProduct(Long productId);
}
