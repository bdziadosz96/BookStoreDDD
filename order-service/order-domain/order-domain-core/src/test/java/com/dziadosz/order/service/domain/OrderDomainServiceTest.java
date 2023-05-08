package com.dziadosz.order.service.domain;

import com.dziadosz.domain.valueobject.CartId;
import com.dziadosz.domain.valueobject.Money;
import com.dziadosz.domain.valueobject.OrderBookId;
import com.dziadosz.domain.valueobject.OrderId;
import com.dziadosz.order.service.domain.entity.Cart;
import com.dziadosz.order.service.domain.entity.Order;
import com.dziadosz.order.service.domain.entity.OrderBook;
import com.dziadosz.order.service.domain.event.order.OrderCreateEvent;
import com.dziadosz.order.service.domain.exception.DomainException;
import com.dziadosz.order.service.domain.valueobject.DeliveryAddress;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

class OrderDomainServiceTest {
    private static final CartId CART_ID = new CartId(UUID.fromString("35441f33-a94e-4c4a-a9dc-442d8b4a6fca"));

    OrderDomainService orderDomainService = new OrderDomainServiceImpl();

    @Test
    @DisplayName("Contract between Order and Cart should be immutable")
    void validateAndInitiateOrder() {
        Cart givenCart = givenCart();

        OrderCreateEvent orderCreateEvent = orderDomainService.validateAndInitiateOrder(givenCart);
        Order order = orderCreateEvent.getOrder();

        OrderId givenOrderId = givenCart.getOrderId();
        OrderId createdOrderId = order.getId();
        Assertions.assertEquals(createdOrderId, givenOrderId);

        CartId createdCartId = order.getCart().getId();
        CartId givenCartId = givenCart.getId();
        Assertions.assertEquals(createdCartId, givenCartId);
    }

    @Test
    @DisplayName("Should throw exception when order total price are different than calculated")
    void validateAndInitiateOrderCheckOrderBooksTotalPriceAreDifferent() {
        Cart givenCart = givenCartWithIncorrectTotalPrice();

        DomainException ex = Assertions.assertThrows(DomainException.class, () -> orderDomainService.validateAndInitiateOrder(givenCart));

        Assertions.assertEquals("Calculated price is incorrect 0", ex.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when order total price are exceeding cart total price limit")
    void validateAndInitiateOrderCheckOrderBooksTotalPriceExceedLimit() {
        Cart givenCart = givenCartWithExceededTotalPrice();

        DomainException ex = Assertions.assertThrows(DomainException.class, () -> orderDomainService.validateAndInitiateOrder(givenCart));

        Assertions.assertEquals("Order limit exceeded 5001.00", ex.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when order id is null")
    void validateAndInitiateOrderCheckOrderFromCartWhenIdNull() {
        Cart cart = Mockito.mock(Cart.class);
        doNothing().when(cart).validateOrderBooks();

        DomainException ex = Assertions.assertThrows(DomainException.class, () -> orderDomainService.validateAndInitiateOrder(cart));

        Assertions.assertEquals("Initialization order failure!", ex.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when order id is null")
    void validateAndInitiateOrderCheckOrderFromCartWhenCollectionNull() {
        Cart cart = Mockito.mock(Cart.class);
        doNothing().when(cart).validateOrderBooks();
        doReturn(new OrderId(UUID.randomUUID())).when(cart).getOrderId();

        DomainException ex = Assertions.assertThrows(DomainException.class, () -> orderDomainService.validateAndInitiateOrder(cart));

        Assertions.assertEquals("Initialization cart failure!", ex.getMessage());
    }

    private Cart givenCartWithExceededTotalPrice() {
        return Cart.Builder.builder()
                .id(CART_ID)
                .orderBooks(givenOrderBooksWithExceededLimit())
                .deliveryAddress(givenDeliveryAddress())
                .totalPrice(new Money(BigDecimal.valueOf(5001)))
                .build();
    }

    private Cart givenCartWithIncorrectTotalPrice() {
        return Cart.Builder.builder()
                .id(CART_ID)
                .orderBooks(givenOrderBooks())
                .deliveryAddress(givenDeliveryAddress())
                .totalPrice(new Money(BigDecimal.ZERO))
                .build();
    }

    private Cart givenCart() {
        return Cart.Builder.builder()
                .id(CART_ID)
                .orderBooks(givenOrderBooks())
                .deliveryAddress(givenDeliveryAddress())
                .totalPrice(new Money(BigDecimal.TEN))
                .build();
    }

    private DeliveryAddress givenDeliveryAddress() {
        return new DeliveryAddress("street", "city", "00-123");
    }

    private List<OrderBook> givenOrderBooksWithExceededLimit() {
        return List.of(
                OrderBook.Builder.builder()
                        .id(new OrderBookId("1"))
                        .name("name")
                        .author("author")
                        .price(new Money(BigDecimal.valueOf(5001)))
                        .quantity(1)
                        .build()
        );
    }

    private List<OrderBook> givenOrderBooks() {
        return List.of(
                OrderBook.Builder.builder()
                        .id(new OrderBookId("1"))
                        .name("name")
                        .author("author")
                        .price(new Money(BigDecimal.TEN))
                        .quantity(1)
                        .build()
        );
    }
}