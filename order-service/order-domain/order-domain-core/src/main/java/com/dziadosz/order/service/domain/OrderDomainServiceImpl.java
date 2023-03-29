package com.dziadosz.order.service.domain;

import com.dziadosz.order.service.domain.entity.Cart;
import com.dziadosz.order.service.domain.entity.Order;
import com.dziadosz.order.service.domain.event.OrderCancelledEvent;
import com.dziadosz.order.service.domain.event.OrderCancellingEvent;
import com.dziadosz.order.service.domain.event.OrderCreateEvent;
import com.dziadosz.order.service.domain.event.OrderExpireEvent;
import com.dziadosz.order.service.domain.event.OrderPaidEvent;

public class OrderDomainServiceImpl implements OrderDomainService {
    @Override
    public OrderCreateEvent validateAndInitiate(final Cart cart) {
        return null;
    }

    @Override
    public OrderPaidEvent payOrder(final Order order) {
        return null;
    }

    @Override
    public OrderExpireEvent expireOrder(final Order order) {
        return null;
    }

    @Override
    public OrderCancellingEvent cancellingOrder(final Order order) {
        return null;
    }

    @Override
    public OrderCancelledEvent canceledOrder(final Order order) {
        return null;
    }
}
