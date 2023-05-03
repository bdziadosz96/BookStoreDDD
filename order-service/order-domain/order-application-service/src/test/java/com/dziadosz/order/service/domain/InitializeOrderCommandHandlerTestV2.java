package com.dziadosz.order.service.domain;

import com.dziadosz.domain.valueobject.CartId;
import com.dziadosz.domain.valueobject.Money;
import com.dziadosz.domain.valueobject.OrderId;
import com.dziadosz.domain.valueobject.OrganisationId;
import com.dziadosz.order.service.domain.dto.command.InitializeOrderCommand;
import com.dziadosz.order.service.domain.dto.command.OrderBook;
import com.dziadosz.order.service.domain.entity.Order;
import com.dziadosz.order.service.domain.entity.Organisation;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InitializeOrderCommandHandlerTestV2 {
    private static final OrganisationId ORGANISATION_ID = new OrganisationId(UUID.randomUUID());
    private static final OrderId ORDER_ID = new OrderId(UUID.randomUUID());
    private static final CartId CART_ID = new CartId(UUID.randomUUID());
    private static final String ORDER_BOOK_ID = UUID.randomUUID().toString();

    OrderRepository orderRepository = mock(OrderRepository.class);
    OrganisationRepository organisationRepository = mock(OrganisationRepository.class);
    PaymentRequestMessagePublisher paymentRequestMessagePublisher = mock(PaymentRequestMessagePublisher.class);
    CartDataMapper cartDataMapper = new CartDataMapper();
    OrderDataMapper orderDataMapper = new OrderDataMapper();

    OrderDomainService orderDomainService = new OrderDomainServiceImpl();
    InitializeOrderCommandHandler initializeOrderCommandHandler = new InitializeOrderCommandHandler(
            orderDomainService,
            orderRepository,
            organisationRepository,
            cartDataMapper,
            orderDataMapper,
            paymentRequestMessagePublisher
    );


    @Test
    void initalizeOrder() {
        var command = givenInitializeOrderCommand();

        when(organisationRepository.findById(ORGANISATION_ID))
                .thenReturn(Optional.of(Organisation.Builder
                        .builder()
                        .id(ORGANISATION_ID)
                        .build()));
        when(orderRepository.save(any(Order.class)))
                        .thenReturn(Order.Builder
                                .builder()
                                .id(ORDER_ID)
                                .build());

        initializeOrderCommandHandler.initalizeOrder(command);
    }

    private static InitializeOrderCommand givenInitializeOrderCommand() {
        return new InitializeOrderCommand(
                new Money(new BigDecimal(10)),
                List.of(new OrderBook(ORDER_BOOK_ID, "name", " author", new Money(BigDecimal.TEN),1)),
                new DeliveryAddress("Example", "Example", "Example"),
                ORGANISATION_ID
        );
    }
}