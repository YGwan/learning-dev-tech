package org.example.order;

import org.example.order.domain.Order;
import org.example.product.ProductFixture;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {

    @Test
    void getTotalPrice() {
        final Order order = new Order(ProductFixture.product(), 2);

        final int totalPrice = order.getTotalPrice();

        assertThat(totalPrice).isEqualTo(2000);
    }
}
