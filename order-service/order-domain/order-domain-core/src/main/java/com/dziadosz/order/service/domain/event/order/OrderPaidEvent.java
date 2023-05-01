package com.dziadosz.order.service.domain.event.order;

import com.dziadosz.domain.event.DomainEvent;
import com.dziadosz.order.service.domain.entity.Order;
import java.time.LocalDateTime;

public class OrderPaidEvent implements DomainEvent<Order> {
    private final Order order;
    private final LocalDateTime dateTime;

    public OrderPaidEvent(final Order order, final LocalDateTime dateTime) {
        this.order = order;
        this.dateTime = dateTime;
    }

    public Order getOrder() {
        return order;
    }

    LocalDateTime getDateTime() {
        return dateTime;
    }
}
