package org.example.product;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductRepository {

    private final Map<Long, Product> inMemoryDB = new HashMap<>();

    private Long sequence = 0L;

    public void save(Product product) {
        product.assignId(++sequence);
        inMemoryDB.put(product.getId(), product);
    }
}
