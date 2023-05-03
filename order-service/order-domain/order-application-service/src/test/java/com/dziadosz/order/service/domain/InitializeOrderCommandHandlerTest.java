package com.dziadosz.order.service.domain;

import com.dziadosz.domain.valueobject.CartId;
import com.dziadosz.domain.valueobject.Money;
import com.dziadosz.domain.valueobject.OrderId;
import com.dziadosz.domain.valueobject.OrganisationId;
import com.dziadosz.order.service.domain.dto.command.InitializeOrderCommand;
import com.dziadosz.order.service.domain.entity.Organisation;
import com.dziadosz.order.service.domain.mapper.CartDataMapper;
import com.dziadosz.order.service.domain.mapper.OrderDataMapper;
import com.dziadosz.order.service.domain.ports.out.message.publisher.payment.PaymentRequestMessagePublisher;
import com.dziadosz.order.service.domain.ports.out.repository.OrderRepository;
import com.dziadosz.order.service.domain.ports.out.repository.OrganisationRepository;
import com.dziadosz.order.service.domain.valueobject.DeliveryAddress;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @BeforeEach
    void initializeTestCases() {

    }

    @Test
    void initalizeOrder() {
        var command = givenInitializeOrderCommand();
        Mockito.when(organisationRepository.findById(ORGANISATION_ID))
                .thenReturn(Optional.of(Organisation.Builder
                .builder()
                .id(ORGANISATION_ID)
                .build()));

        initializeOrderCommandHandler.initalizeOrder(command);
    }

    private static InitializeOrderCommand givenInitializeOrderCommand() {
        return new InitializeOrderCommand(
                new Money(new BigDecimal(10)),
                Collections.emptyList(),
                new DeliveryAddress("Example", "Example", "Example"),
                ORGANISATION_ID
        );
    }
}