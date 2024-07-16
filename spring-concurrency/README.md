# Spring 동시성 처리

## 동시성이란?

- 동시성 문제는 하나의 자원에 대해서 여러 쓰레드가 동시에 접근하여 수정하는 경우에 발생하는 문제
- 임계 영역에 한번의 하나의 쓰레드의 접근만을 허용해야되는데, 멀티 쓰레드 환경에서 동시에 임계영역에 접근한 상황에서 발생하는 문제를 의미
- 데이터 정합성 문제가 발생할 수 있다.

<br>

## 해결 방법

### 1. Thread-safe한 자료구조 사용
    - ConcurrentHashMap
    - AtomicInteger
    - BlockingQueue
    - 등...

구현이 쉽지만 한계가 명확히 존재 (특정 부분의 특정 로직만 사용이 가능)
  
<br>

### 2. Synchronized 사용
``` java

//    @Transactional
public synchronized void decrease(Long id, Long quantity) {
    // 로직    
}

```

- 여러 개의 WAS를 사용하는 환경에서는 동시성 처리가 불가능하다. 
  - 인스턴스 단위로 thread-safe가 보장되기 때문에 (여러 WAS면 여러 인스턴스가 존재)

- 트랜잭션 처리가 불가능하다.
  - @Transactional 어노테이션 처리로 인한 AOP 처리로 인해 동시성 문제 발생

<br>

### 3. 비관적 락(Pessimistic Lock) 사용
- 데이터 갱신 시 충돌이 발생할 것으로 예상하고 미리 잠금을 하는 방식
- 데드락이 발생할 수 있다.
- 락이 필요하지 않은 상황에서도 기본적으로 락을 잡기 때문에 안전하지만, 성능 저하가 발생할 수 있다.
- DB 단에서 동시성 처리

``` java
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("select s from Stock s where s.productId = :productId")
Optional<Stock> findByProductIdWithPessimisticLock(Long productId);

```

<br>

### 4. 낙관적 락(Optimistic Lock) 사용
- 락을 먼저 선점하는 방식이 아닌 동시성 문제가 발생하면 처리
- 실제로 락을 거는 것이 아닌 버전을 이용하여 정합성을 맞추는 방식
- 락을 미리 선점하지 않기 때문에 성능 상 좋을 수 있지만, 충돌이 발생한 경우 롤백이나 재시도 처리 등이 필요하기 때문에 비효율적일 수 있다.
- 애플리케이션 단에서 동시성 처리

- 낙관적 락 적용 repository
  ``` java
  @Lock(LockModeType.OPTIMISTIC)
  @Query("select s from Stock s where s.productId = :productId")
  Optional<Stock> findByProductIdWithOptimisticLock(Long productId);
  
  ```

<br>

- 재시도 처리 - 실패시 단순 재시도 무한반복 (50ms 만큼 대기 후 재시도)
  ``` java
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
  
  ```

<br>

### 정리
- 비관적 락은 데이터 정합성이 중요하고 충돌이 많이 발생할 것 같은 경우에 사용하기 좋습니다.
- 낙관적 락은 데이터 충돌이 자주 일어나지 않는 경우에 사용하기 좋습니다.

<br>

### 5. Named Lock
- Lock을 위한 별도의 공간에 Lock을 설정하고 해제하기 전까지 다른 쓰레드가 Lock을 획득할 수 없다.
- Transaction이 끝난 후에 Lock이 해제되는 것이 아닌, 별도의 명령어로 해제를 수행해주거나 선점시간이 끝나야 해제된다.
- 다른 데이터 소스에서 Lock 관련 처리를 진행하는 것이 좋다. ( connection pool의 낭비 문제로 인해)

- Lock 처리 관련 repository
  ``` java
  @Query(value = "select get_lock(:key,3000)", nativeQuery = true)
  void getLock(String key);
  
  @Query(value = "select release_lock(:key)", nativeQuery = true)
  void releaseLock(String key);
  ```

<br>

- 적용
  ``` java
    @Transactional
    public void decrease(Long productId, Long quantity) {
        try {
            lockRepository.getLock(productId.toString());
            stockService.decrease(productId, quantity);
        } finally {
            lockRepository.releaseLock(productId.toString());
        }
    }
  ```
  
<br>

### 6. Redis Lettuce
- 기본 Redis의 라이브러리로 구현 가능
- Setnx 명령어를 활용하여 분산락을 구현
- SpinLock 방식으로 락을 획득하기 때문에 락을 획득 못했을때의 재시도 로직을 작성해야한다.
- 계속해서 락 점유 시도를 하기 때문에 레디스가 계속 부하를 받게 되어 응답 시간이 지연될 수 있다.

- RedisRepository
  ``` java
  @Component
  public class RedisLockRepository {

    private final RedisTemplate<String, String> restTemplate;

    public RedisLockRepository(RedisTemplate<String, String> restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Boolean lock(Long key) {
        return restTemplate
                .opsForValue()
                .setIfAbsent(key.toString(), "lock", Duration.ofMillis(3_000));
    }

    public Boolean unLock(Long key) {
        return restTemplate.delete(key.toString());
    }
  }
  ```

<br>

- 적용
  ``` java
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
  ```

<br>

### 7. Redisson Lock
 - 추가적인 라이브러리 필요 (Redisson)
 - pub & sub 방식으로 분산락을 구현
 - Redisson 은 별도의 Lock interface를 지원하기 때문에 retry, timout과 같은 로직을 추가로 작성할 필요가 없다.
 - 락이 해제되면 락을 subscribe 하는 클라이언트는 락이 해제되었다는 신호를 받고 락 획득을 시도

``` java
public void decrease(Long productId, Long quantity) {
    RLock lock = redissonClient.getLock(productId.toString());

    try {
        boolean available = lock.tryLock(5, 1, TimeUnit.SECONDS);

        if (!available) {
            System.out.println("lock 획득 실패");
            decrease(productId, quantity);
            return;
        }

        stockService.decrease(productId, quantity);

    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    } finally {
        lock.unlock();
    }
}
```

<br>
<br>
<br>
