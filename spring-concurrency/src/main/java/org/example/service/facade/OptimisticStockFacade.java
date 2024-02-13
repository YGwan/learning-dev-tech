package org.example.service.facade;

import org.example.service.OptimisticStockService;
import org.springframework.stereotype.Component;

@Component
public class OptimisticStockFacade {

    private final OptimisticStockService stockService;

    public OptimisticStockFacade(OptimisticStockService stockService) {
        this.stockService = stockService;
    }

    public void decrease(Long productId, Long quantity) throws InterruptedException {
        while (true) {
            try {
                stockService.decrease(productId, quantity);
                break;
            } catch (Exception e) {
                Thread.sleep(50);
            }
        }
    }
}
