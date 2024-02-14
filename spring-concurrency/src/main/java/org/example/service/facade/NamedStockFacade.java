package org.example.service.facade;

import org.example.repository.LockRepository;
import org.example.service.NamedStockService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NamedStockFacade {

    private final LockRepository lockRepository;

    private final NamedStockService stockService;

    public NamedStockFacade(LockRepository lockRepository, NamedStockService stockService) {
        this.lockRepository = lockRepository;
        this.stockService = stockService;
    }

    @Transactional
    public void decrease(Long productId, Long quantity) {
        try {
            lockRepository.getLock(productId.toString());
            stockService.decrease(productId, quantity);
        } finally {
            lockRepository.releaseLock(productId.toString());
        }
    }
}
