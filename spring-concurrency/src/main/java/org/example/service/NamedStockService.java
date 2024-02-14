package org.example.service;

import org.example.domain.Stock;
import org.example.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NamedStockService {

    private final StockRepository stockRepository;

    public NamedStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void decrease(Long id, Long quantity) {
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