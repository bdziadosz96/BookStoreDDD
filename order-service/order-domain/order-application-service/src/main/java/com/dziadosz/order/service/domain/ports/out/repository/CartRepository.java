package com.dziadosz.order.service.domain.ports.out.repository;

import com.dziadosz.order.service.domain.entity.Cart;

public interface CartRepository {
    Cart save(Cart cart);
}
