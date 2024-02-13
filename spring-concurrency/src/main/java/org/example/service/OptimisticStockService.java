package org.example.service;

import org.example.domain.Stock;
import org.example.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptimisticStockService {

    private final StockRepository stockRepository;

    public OptimisticStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void decrease(Long productId, Long quantity) {
        Stock stock = stockRepository.findByProductIdWithOptimisticLock(productId).orElseThrow(
                () -> new IllegalArgumentException("재고를 찾을 수 없습니다.")
        );
        stock.decreaseQuantity(quantity);
        stockRepository.save(stock);
    }
}

