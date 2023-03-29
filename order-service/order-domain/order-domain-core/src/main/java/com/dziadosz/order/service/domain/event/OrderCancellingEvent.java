package com.dziadosz.order.service.domain.event;

import com.dziadosz.domain.event.DomainEvent;
import com.dziadosz.order.service.domain.entity.Order;
import java.time.LocalDateTime;

public class OrderCancellingEvent implements DomainEvent<Order> {
    private final Order order;
    private final LocalDateTime dateTime;

    OrderCancellingEvent(final Order order, final LocalDateTime dateTime) {
        this.order = order;
        this.dateTime = dateTime;
    }

    Order getOrder() {
        return order;
    }

    LocalDateTime getDateTime() {
        return dateTime;
    }
}
