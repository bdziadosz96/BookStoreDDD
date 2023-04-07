package com.dziadosz.order.service.domain.entity;

import com.dziadosz.domain.entity.AggregateRoot;
import com.dziadosz.domain.valueobject.Money;
import com.dziadosz.domain.valueobject.OrderId;
import com.dziadosz.domain.valueobject.OrderStatus;
import com.dziadosz.domain.valueobject.TrackingPaymentId;
import com.dziadosz.order.service.domain.exception.DomainException;
import java.util.List;
import java.util.UUID;

public class Order extends AggregateRoot<OrderId> {
    private final Cart cart;
    private final Money price;
    private TrackingPaymentId trackingPaymentId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;

    private Order(final Builder builder) {
        super.setId(builder.orderId);
        cart = builder.cart;
        price = builder.price;
        trackingPaymentId = builder.trackingPaymentId;
        orderStatus = builder.orderStatus;
        failureMessages = builder.failureMessages;
    }

    public void pay() {
        if (orderStatus != OrderStatus.APPROVED) {
            throw new DomainException("Order is not in correct state for pay operation");
        }
        orderStatus = OrderStatus.PAID;
    }

    public void expire() {
        if (orderStatus != OrderStatus.APPROVED) {
            throw new DomainException("Order is not in correct state for outdate operation");
        }
        orderStatus = OrderStatus.EXPIRED;
    }

    public void initCancel(List<String> failureMessages) {
        if (orderStatus != OrderStatus.PAID) {
            throw new DomainException("Order is not in correct state for init cancelling operation");
        }
        orderStatus = OrderStatus.CANCELLING;
    }

    public void cancel(List<String> failureMessages) {
        if (orderStatus != OrderStatus.CANCELLING) {
            throw new DomainException("Order is not in correct state for cancelling operation");
        }
        orderStatus = OrderStatus.CANCELLED;
        updateFailures(failureMessages);
    }

    private void updateFailures(final List<String> failureMessages) {
        if (this.failureMessages != null && failureMessages != null) {
            this.failureMessages.addAll(failureMessages);
        }

        if (this.failureMessages == null) {
            this.failureMessages = failureMessages;
        }
    }

    public void validateOrder() {
        validateInitialOrder();
        validateInitialCart();
    }

    private void validateInitialCart() {
        if (cart.getTotalPrice() != null || cart.getOrderBooks() != null) {
            throw new DomainException("Initialization cart failure!");
        }
    }

    private void validateInitialOrder() {
        if (orderStatus != null || getId() != null) {
            throw new DomainException("Initizalition order failure!");
        }
    }

    Cart getCart() {
        return cart;
    }

    public static Order fromCart(Cart cart) {
        return Builder
                .builder()
                .id(cart.getOrderId())
                .trackingPaymentId(new TrackingPaymentId(UUID.randomUUID()))
                .cart(cart)
                .orderStatus(OrderStatus.APPROVED)
                .build();
    }

    Money getPrice() {
        return price;
    }

    TrackingPaymentId getTrackingPaymentId() {
        return trackingPaymentId;
    }

    OrderStatus getOrderStatus() {
        return orderStatus;
    }

    List<String> getFailureMessages() {
        return failureMessages;
    }

    public static final class Builder {
        private OrderId orderId;
        private Cart cart;
        private Money price;
        private TrackingPaymentId trackingPaymentId;
        private OrderStatus orderStatus;
        private List<String> failureMessages;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(final OrderId val) {
            orderId = val;
            return this;
        }

        public Builder cart(final Cart val) {
            cart = val;
            return this;
        }

        public Builder price(final Money val) {
            price = val;
            return this;
        }

        public Builder trackingPaymentId(final TrackingPaymentId val) {
            trackingPaymentId = val;
            return this;
        }

        public Builder orderStatus(final OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessages(final List<String> val) {
            failureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "cart=" + cart +
                ", price=" + price +
                ", trackingPaymentId=" + trackingPaymentId +
                ", orderStatus=" + orderStatus +
                ", failureMessages=" + failureMessages +
                '}';
    }
}
