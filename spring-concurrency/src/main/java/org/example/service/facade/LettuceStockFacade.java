package org.example.service.facade;

import org.example.redis.RedisLockRepository;
import org.example.service.StockService;
import org.springframework.stereotype.Component;

@Component
public class LettuceStockFacade {

    private final RedisLockRepository redisLockRepository;

    private final StockService stockService;

    public LettuceStockFacade(RedisLockRepository redisLockRepository, StockService stockService) {
        this.redisLockRepository = redisLockRepository;
        this.stockService = stockService;
    }

    public void decrease(Long productId, Long quantity) throws InterruptedException {
        while (!redisLockRepository.lock(productId)) {
            Thread.sleep(100);
        }

        try {
            stockService.decrease(productId, quantity);
        } finally {
            redisLockRepository.unLock(productId);
        }
    }
}
