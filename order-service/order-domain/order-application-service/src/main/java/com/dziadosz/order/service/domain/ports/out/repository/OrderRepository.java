package com.dziadosz.order.service.domain.ports.out.repository;

import com.dziadosz.domain.valueobject.CartId;
import com.dziadosz.order.service.domain.entity.Order;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findByCartId(CartId cartId);
}
