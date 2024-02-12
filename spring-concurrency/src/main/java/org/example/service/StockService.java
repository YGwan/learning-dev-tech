package org.example.service;

import org.example.domain.Stock;
import org.example.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock findById(Long id) {
        return getById(id);
    }

    @Transactional
    public void decrease(Long id, Long quantity) {
        Stock stock = getById(id);
        stock.decreaseQuantity(quantity);
    }

    private Stock getById(Long id) {
        return stockRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("재고를 찾을 수 없습니다.")
        );
    }
}
