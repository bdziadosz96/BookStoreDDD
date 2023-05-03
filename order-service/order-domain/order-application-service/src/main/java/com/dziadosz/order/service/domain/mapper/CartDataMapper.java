package com.dziadosz.order.service.domain.mapper;

import com.dziadosz.domain.annotations.Mapper;
import com.dziadosz.domain.valueobject.OrderBookId;
import com.dziadosz.order.service.domain.dto.command.InitializeOrderCommand;
import com.dziadosz.order.service.domain.entity.Cart;
import com.dziadosz.order.service.domain.entity.OrderBook;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CartDataMapper {

    public Cart initalizeCartCommandToCart(InitializeOrderCommand command) {
        Cart build = Cart.Builder.builder()
                .deliveryAddress(command.deliveryAddress())
                .orderBooks(toOrderBooks(command.orderBooks()))
                .totalPrice(command.totalPrice())
                .build();
        log.info(build.toString());
        return build;
    }

    private List<OrderBook> toOrderBooks(final List<com.dziadosz.order.service.domain.dto.command.OrderBook> orderBooks) {
        return orderBooks.stream()
                .map(this::toOrderBook)
                .collect(Collectors.toList());
    }

    private OrderBook toOrderBook(final com.dziadosz.order.service.domain.dto.command.OrderBook orderBook) {
        return OrderBook.Builder
                .builder()
                .id(new OrderBookId(orderBook.id()))
                .name(orderBook.name())
                .author(orderBook.author())
                .price(orderBook.price())
                .quantity(orderBook.quantity())
                .build();
    }
}
