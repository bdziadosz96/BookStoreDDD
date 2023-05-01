package com.dziadosz.order.service.domain.dto.command;

import com.dziadosz.domain.valueobject.Money;
import com.dziadosz.order.service.domain.dto.cart.command.OrderBook;
import com.dziadosz.order.service.domain.dwa.command.OrderBook;

public class Tester {
    public static void main(String[] args) {
        OrderBook orderBook = new OrderBook(
                null,
                "pasta",
                Money.ZERO,
                null
        );
    }
}
