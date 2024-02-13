package org.example.service;

import org.example.domain.Stock;
import org.example.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PessimisticStockService {

    private final StockRepository stockRepository;

    public PessimisticStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock findByProductId(Long productId) {
        return getByProductId(productId);
    }

    @Transactional
    public void decrease(Long productId, Long quantity) {
        Stock stock = stockRepository.findByProductIdWithPessimisticLock(productId).orElseThrow(
                () -> new IllegalArgumentException("재고를 찾을 수 없습니다.")
        );
        stock.decreaseQuantity(quantity);
        stockRepository.save(stock);
    }

    private Stock getByProductId(Long productId) {
        return stockRepository.findByProductId(productId).orElseThrow(
                () -> new IllegalArgumentException("재고를 찾을 수 없습니다.")
        );
    }
}

