package com.dziadosz.order.service.domain;

import com.dziadosz.order.service.domain.entity.Cart;
import com.dziadosz.order.service.domain.entity.Order;
import com.dziadosz.order.service.domain.event.order.OrderCancelledEvent;
import com.dziadosz.order.service.domain.event.order.OrderCancellingEvent;
import com.dziadosz.order.service.domain.event.order.OrderCreateEvent;
import com.dziadosz.order.service.domain.event.order.OrderExpireEvent;
import com.dziadosz.order.service.domain.event.order.OrderPaidEvent;
import java.util.List;

public interface OrderDomainService {
    OrderCreateEvent validateAndInitiateOrder(Cart cart);

    OrderPaidEvent payOrder(Order order);

    OrderExpireEvent expireOrder(Order order);

    OrderCancellingEvent cancellingOrder(Order order, List<String> failures);

    OrderCancelledEvent canceledOrder(Order order, List<String> failures);
}
