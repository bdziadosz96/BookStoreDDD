package com.dziadosz.order.service.domain.entity;

import com.dziadosz.domain.entity.BaseEntity;
import com.dziadosz.domain.valueobject.CartId;
import com.dziadosz.domain.valueobject.Money;
import com.dziadosz.domain.valueobject.OrderId;
import com.dziadosz.order.service.domain.exception.DomainException;
import com.dziadosz.order.service.domain.valueobject.DeliveryAddress;
import java.util.List;
import java.util.UUID;

public class Cart extends BaseEntity<CartId> {
    private OrderId orderId;
    private Money totalPrice;
    private final List<OrderBook> orderBooks;
    private final DeliveryAddress deliveryAddress;

    private Cart(final Builder builder) {
        super.setId(builder.cartId);
        orderBooks = builder.orderBooks;
        totalPrice = builder.totalPrice;
        deliveryAddress = builder.deliveryAddress;
    }

    public void validateOrderBooks() {
        var orderBooksCalculatedPrice = orderBooks.stream()
                .map(OrderBook::calculateTotalPrice)
                .reduce(Money.ZERO, Money::add);

        if (!orderBooksCalculatedPrice.isEqualTo(totalPrice) || !orderBooksCalculatedPrice.isGreaterThanZero()) {
            throw new DomainException("Validation in order books failed");
        }
    }

    OrderId getOrderId() {
        return orderId;
    }

    List<OrderBook> getOrderBooks() {
        return orderBooks;
    }

    Money getTotalPrice() {
        return totalPrice;
    }

    DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    void assign(final OrderId orderId, final CartId cartId) {
        super.setId(cartId);
        this.orderId = orderId;
    }

    public static final class Builder {
        private CartId cartId;
        private List<OrderBook> orderBooks;
        private Money totalPrice;
        private DeliveryAddress deliveryAddress;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(final CartId val) {
            cartId = val;
            return this;
        }

        public Builder orderBooks(final List<OrderBook> val) {
            orderBooks = val;
            return this;
        }

        public Builder totalPrice(final Money val) {
            totalPrice = val;
            return this;
        }

        public Builder deliveryAddress(final DeliveryAddress val) {
            deliveryAddress = val;
            return this;
        }

        public Cart build() {
            return new Cart(this);
        }
    }

    @Override
    public String toString() {
        return "Cart{" +
                "orderId=" + orderId +
                ", totalPrice=" + totalPrice +
                ", orderBooks=" + orderBooks +
                ", deliveryAddress=" + deliveryAddress +
                '}';
    }
}
