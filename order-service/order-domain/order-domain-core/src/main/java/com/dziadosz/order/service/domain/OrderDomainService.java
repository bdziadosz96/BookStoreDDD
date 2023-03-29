package com.dziadosz.order.service.domain;

import com.dziadosz.order.service.domain.entity.Cart;
import com.dziadosz.order.service.domain.entity.Order;
import com.dziadosz.order.service.domain.event.OrderCancelledEvent;
import com.dziadosz.order.service.domain.event.OrderCancellingEvent;
import com.dziadosz.order.service.domain.event.OrderCreateEvent;
import com.dziadosz.order.service.domain.event.OrderExpireEvent;
import com.dziadosz.order.service.domain.event.OrderPaidEvent;

public interface OrderDomainService {
    OrderCreateEvent validateAndInitiate(Cart cart);

    OrderPaidEvent payOrder(Order order);

    OrderExpireEvent expireOrder(Order order);

    OrderCancellingEvent cancellingOrder(Order order);

    OrderCancelledEvent canceledOrder(Order order);
}
