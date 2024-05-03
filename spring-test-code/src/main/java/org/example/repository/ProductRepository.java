package org.example.repository;

import org.example.domain.Product;
import org.example.domain.constant.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByProductStatusIn(List<ProductStatus> statuses);
}
