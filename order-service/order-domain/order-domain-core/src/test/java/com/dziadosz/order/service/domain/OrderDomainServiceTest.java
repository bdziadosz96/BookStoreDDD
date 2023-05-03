package com.dziadosz.order.service.domain;

import com.dziadosz.domain.valueobject.CartId;
import com.dziadosz.domain.valueobject.Money;
import com.dziadosz.domain.valueobject.OrderBookId;
import com.dziadosz.domain.valueobject.OrderId;
import com.dziadosz.order.service.domain.entity.Cart;
import com.dziadosz.order.service.domain.entity.Order;
import com.dziadosz.order.service.domain.entity.OrderBook;
import com.dziadosz.order.service.domain.event.order.OrderCreateEvent;
import com.dziadosz.order.service.domain.valueobject.DeliveryAddress;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderDomainServiceTest {
    private static final CartId CART_ID = new CartId(UUID.fromString("35441f33-a94e-4c4a-a9dc-442d8b4a6fca"));
    private static final OrderId ORDER_ID = new OrderId(UUID.fromString("ba65ac3c-e0c1-4bef-8893-57540b3269d6"));

    OrderDomainService orderDomainService = new OrderDomainServiceImpl();

    @Test
    @DisplayName("Contract between Order and Cart should be immutable")
    void validateAndInitiateOrder() {
        Cart cart = givenCart();

        OrderCreateEvent orderCreateEvent = orderDomainService.validateAndInitiateOrder(cart);
        Order order = orderCreateEvent.getOrder();

        Assertions.assertEquals(order.getId(), cart.getOrderId());
        Assertions.assertEquals(order.getCart().getId(), cart.getId());

    }

    private Cart givenCart() {
        return Cart.Builder.builder()
                .id(CART_ID)
                .orderBooks(givenOrderBooks())
                .deliveryAddress(givenDeliveryAddress())
                .totalPrice(new Money(BigDecimal.TEN))
                .build();
    }

    private DeliveryAddress givenDeliveryAddress() {
        return new DeliveryAddress("street", "city", "00-123");
    }

    private List<OrderBook> givenOrderBooks() {
        return List.of(
                OrderBook.Builder.builder()
                        .id(new OrderBookId("1"))
                        .name("name")
                        .author("author")
                        .price(new Money(BigDecimal.TEN))
                        .quantity(1)
                        .build()
        );
    }
}