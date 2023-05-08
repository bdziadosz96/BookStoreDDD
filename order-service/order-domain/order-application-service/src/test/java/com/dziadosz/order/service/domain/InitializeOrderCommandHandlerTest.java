package com.dziadosz.order.service.domain;

import com.dziadosz.domain.valueobject.CartId;
import com.dziadosz.domain.valueobject.Money;
import com.dziadosz.domain.valueobject.OrderId;
import com.dziadosz.domain.valueobject.OrganisationId;
import com.dziadosz.order.service.domain.dto.command.InitializeOrderCommand;
import com.dziadosz.order.service.domain.dto.command.OrderBook;
import com.dziadosz.order.service.domain.dto.response.InitializeOrderResponse;
import com.dziadosz.order.service.domain.entity.Order;
import com.dziadosz.order.service.domain.entity.Organisation;
import com.dziadosz.order.service.domain.exception.DomainException;
import com.dziadosz.order.service.domain.mapper.CartDataMapper;
import com.dziadosz.order.service.domain.mapper.OrderDataMapper;
import com.dziadosz.order.service.domain.ports.out.message.publisher.payment.PaymentRequestMessagePublisher;
import com.dziadosz.order.service.domain.ports.out.repository.OrderRepository;
import com.dziadosz.order.service.domain.ports.out.repository.OrganisationRepository;
import com.dziadosz.order.service.domain.valueobject.DeliveryAddress;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;


@SpringBootTest(classes = TestConfig.class)
class InitializeOrderCommandHandlerTest {
    private static final OrganisationId ORGANISATION_ID = new OrganisationId(UUID.randomUUID());
    private static final OrderId ORDER_ID = new OrderId(UUID.randomUUID());
    private static final CartId CART_ID = new CartId(UUID.randomUUID());

    @Autowired
    CartDataMapper cartDataMapper;
    @Autowired
    OrderDataMapper orderDataMapper;
    @Autowired
    OrganisationRepository organisationRepository;
    @Autowired
    OrderDomainService orderDomainService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PaymentRequestMessagePublisher paymentRequestMessagePublisher;
    @Autowired
    InitializeOrderCommandHandler initializeOrderCommandHandler;

    @Test
    @DisplayName("Order initialization should return no exception with proper id")
    void initalizeOrder() {
        var command = givenInitializeOrderCommand();

        Mockito.when(organisationRepository.findById(ORGANISATION_ID))
                .thenReturn(Optional.of(Organisation.Builder
                .builder()
                .id(ORGANISATION_ID)
                .build()));

        Mockito.when(orderRepository.save(any(Order.class)))
                        .thenReturn(Order.Builder.
                                builder().
                                id(ORDER_ID).
                                build());

        InitializeOrderResponse initializeOrderResponse = initializeOrderCommandHandler.initalizeOrder(command);
        Assertions.assertEquals(ORDER_ID, initializeOrderResponse.orderId());
    }

    @Test
    @DisplayName("Order initiazliation should fail with incorrect order money amount")
    void initalizeOrderShouldFail() {
        var command = givenInitializeIncorrectOrderCommand();

        Mockito.when(organisationRepository.findById(ORGANISATION_ID))
                .thenReturn(Optional.of(Organisation.Builder
                        .builder()
                        .id(ORGANISATION_ID)
                        .build()));

        Mockito.when(orderRepository.save(any(Order.class)))
                .thenReturn(Order.Builder.
                        builder().
                        id(ORDER_ID).
                        build());

        DomainException ex = Assertions.assertThrows(DomainException.class, () -> initializeOrderCommandHandler.initalizeOrder(command));
        Assertions.assertEquals("Calculated price is incorrect 10", ex.getMessage());
    }

    private InitializeOrderCommand givenInitializeOrderCommand() {
        return new InitializeOrderCommand(
                CART_ID,
                new Money(BigDecimal.TEN),
                List.of(new OrderBook("123", "name", "author",
                        new Money(BigDecimal.TEN),1)),
                new DeliveryAddress("Example", "Example", "Example"),
                ORGANISATION_ID
        );
    }

    private InitializeOrderCommand givenInitializeIncorrectOrderCommand() {
        return new InitializeOrderCommand(
                CART_ID,
                new Money(BigDecimal.TEN),
                List.of(new OrderBook("123", "name", "author",
                        new Money(BigDecimal.ZERO),1)),
                new DeliveryAddress("Example", "Example", "Example"),
                ORGANISATION_ID
        );
    }
}