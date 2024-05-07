package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.Order;
import org.example.domain.Product;
import org.example.domain.Stock;
import org.example.domain.constant.ProductType;
import org.example.dto.request.OrderCreateRequest;
import org.example.dto.response.OrderResponse;
import org.example.repository.OrderRepository;
import org.example.repository.ProductRepository;
import org.example.repository.StockRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
        List<Product> products = getProductByProductNumbers(productNumbers);

        deductStockQuantity(products);

        Order order = Order.create(products, registeredDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    private void deductStockQuantity(List<Product> products) {
        List<String> stockProductNumbers = extractStockProductNumbersBy(products);

        Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);
        Map<String, Long> productNumberCountingMap = createCountingMapBy(stockProductNumbers);

        HashSet<String> distinctStockProductNumbers = new HashSet<>(stockProductNumbers);

        for (String productNumber : distinctStockProductNumbers) {
            Stock stock = stockMap.get(productNumber);
            int quantity = productNumberCountingMap.get(productNumber).intValue();

            if (!stock.isQuantityEnough(quantity)) {
                throw new IllegalArgumentException("재고가 충분하지 않은 상품이 존재합니다.");
            }
            stock.deductQuantity(quantity);
        }
    }

    private List<String> extractStockProductNumbersBy(List<Product> products) {
        return products.stream()
                .filter(product -> ProductType.containsStockType(product.getProductType()))
                .map(Product::getProductNumber)
                .collect(Collectors.toList());
    }

    private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
        return stocks.stream()
                .collect(Collectors.toMap(Stock::getProductNumber, stock -> stock));
    }

    private Map<String, Long> createCountingMapBy(List<String> stockProductNumbers) {
        return stockProductNumbers.stream()
                .collect(Collectors.groupingBy(num -> num, Collectors.counting()));
    }

    private List<Product> getProductByProductNumbers(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, product -> product));

        return productNumbers.stream()
                .map(productMap::get)
                .collect(Collectors.toList());
    }
}
