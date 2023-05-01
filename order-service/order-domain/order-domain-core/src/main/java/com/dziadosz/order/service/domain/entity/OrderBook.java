package com.dziadosz.order.service.domain.entity;

import com.dziadosz.domain.entity.BaseEntity;
import com.dziadosz.domain.valueobject.Money;
import com.dziadosz.domain.valueobject.OrderBookId;
import com.dziadosz.order.service.domain.exception.DomainException;
import java.math.BigDecimal;

public class OrderBook extends BaseEntity<OrderBookId> {
    private final String name;
    private final String author;
    private final Money price;
    private final Integer quantity;

    public Money calculateTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public OrderBook(OrderBookId orderBookId,
                     final String name,
                     final String author,
                     final Money price,
                     final Integer quantity) {
        super.setId(orderBookId);
        this.quantity = quantity;
        this.name = name;
        this.author = author;
        this.price = price;
    }

    void validateQuantity() {
        if (!(quantity < 5)) {
            throw new DomainException("Quantity of order book must be lower than 5 pieces");
        }
    }

    String getName() {
        return name;
    }

    String getAuthor() {
        return author;
    }

    Money getPrice() {
        return price;
    }

    Integer getQuantity() {
        return quantity;
    }
}
