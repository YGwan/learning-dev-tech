package org.example.service;

import org.example.domain.Stock;
import org.example.repository.StockRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock findByProductId(Long productId) {
        return getByProductId(productId);
    }

    @Transactional
    public synchronized void decrease(Long id, Long quantity) {
        Stock stock = getByProductId(id);
        stock.decreaseQuantity(quantity);
        stockRepository.saveAndFlush(stock);
    }

    private Stock getByProductId(Long productId) {
        return stockRepository.findByProductId(productId).orElseThrow(
                () -> new IllegalArgumentException("재고를 찾을 수 없습니다.")
        );
    }
}
