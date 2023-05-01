package com.dziadosz.order.service.domain;

import com.dziadosz.order.service.domain.entity.Cart;
import com.dziadosz.order.service.domain.entity.Order;
import com.dziadosz.order.service.domain.entity.Organisation;
import com.dziadosz.order.service.domain.event.order.OrderCancelledEvent;
import com.dziadosz.order.service.domain.event.order.OrderCancellingEvent;
import com.dziadosz.order.service.domain.event.order.OrderCreateEvent;
import com.dziadosz.order.service.domain.event.order.OrderExpireEvent;
import com.dziadosz.order.service.domain.event.order.OrderPaidEvent;
import com.dziadosz.order.service.domain.exception.DomainException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDomainServiceImpl implements OrderDomainService {
    private static final Logger LOGGER = Logger.getLogger(OrderDomainService.class.getName());

    @Override
    public OrderCreateEvent validateAndInitiateOrder(final Cart cart,
                                                     final Organisation organisation) {
        cart.validateOrderBooks();
        validateOrganization(organisation);
        var order = Order.fromCart(cart);
        order.validateOrder();
        LOGGER.log(Level.INFO, "Initialized order " + order.getId());
        return new OrderCreateEvent(order, LocalDateTime.now());
    }

    @Override
    public OrderPaidEvent payOrder(final Order order) {
        order.pay();
        LOGGER.log(Level.INFO, "Paid order " + order.getId());
        return new OrderPaidEvent(order, LocalDateTime.now());
    }

    @Override
    public OrderExpireEvent expireOrder(final Order order) {
        order.expire();
        LOGGER.log(Level.INFO, "Paid order " + order.getId());
        return new OrderExpireEvent(order, LocalDateTime.now());
    }

    @Override
    public OrderCancellingEvent cancellingOrder(final Order order,
                                                final List<String> failures) {
        order.initCancel(failures);
        LOGGER.log(Level.INFO, "Cancelling order initialization " + order.getId());
        return new OrderCancellingEvent(order, LocalDateTime.now());
    }

    @Override
    public OrderCancelledEvent canceledOrder(final Order order,
                                             final List<String> failures) {
        order.cancel(failures);
        LOGGER.log(Level.INFO, "Cancelled order " + order.getId());
        return new OrderCancelledEvent(order, LocalDateTime.now());
    }

    private void validateOrganization(Organisation organisation) {
        if (!organisation.isActive()) {
            throw new DomainException("Organisation with ID " + organisation.getId() +
                    " is not active");
        }
    }
}
