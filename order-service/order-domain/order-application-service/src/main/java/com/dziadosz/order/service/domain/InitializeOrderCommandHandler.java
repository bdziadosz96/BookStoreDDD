package com.dziadosz.order.service.domain;

import com.dziadosz.domain.annotations.CommandHandler;
import com.dziadosz.domain.valueobject.OrganisationId;
import com.dziadosz.order.service.domain.dto.command.InitializeOrderCommand;
import com.dziadosz.order.service.domain.dto.response.InitializeOrderResponse;
import com.dziadosz.order.service.domain.entity.Cart;
import com.dziadosz.order.service.domain.entity.Order;
import com.dziadosz.order.service.domain.event.order.OrderCreateEvent;
import com.dziadosz.order.service.domain.exception.DomainException;
import com.dziadosz.order.service.domain.mapper.CartDataMapper;
import com.dziadosz.order.service.domain.mapper.OrderDataMapper;
import com.dziadosz.order.service.domain.ports.out.message.publisher.payment.PaymentRequestMessagePublisher;
import com.dziadosz.order.service.domain.ports.out.repository.OrderRepository;
import com.dziadosz.order.service.domain.ports.out.repository.OrganisationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@CommandHandler
@Slf4j
public class InitializeOrderCommandHandler {
    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final OrganisationRepository organisationRepository;
    private final CartDataMapper cartDataMapper;
    private final OrderDataMapper orderDataMapper;
    private final PaymentRequestMessagePublisher paymentRequestMessagePublisher;

    InitializeOrderCommandHandler(final OrderDomainService orderDomainService,
                                  final OrderRepository orderRepository,
                                  final OrganisationRepository organisationRepository,
                                  final CartDataMapper cartDataMapper,
                                  final OrderDataMapper orderDataMapper,
                                  final PaymentRequestMessagePublisher paymentRequestMessagePublisher) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.organisationRepository = organisationRepository;
        this.cartDataMapper = cartDataMapper;
        this.orderDataMapper = orderDataMapper;
        this.paymentRequestMessagePublisher = paymentRequestMessagePublisher;
    }

    @Transactional
    InitializeOrderResponse initalizeOrder(final InitializeOrderCommand command) {
        OrderCreateEvent orderCreateEvent = extractOrderFrom(command);
        Order order = persistOrder(orderCreateEvent.getOrder());
        log.info("Order created with id: " + order.getId());
        paymentRequestMessagePublisher.publish(orderCreateEvent);
        return orderDataMapper.orderToInitializeOrderResponse(order);
    }

    private Order persistOrder(final Order orderToPersist) {
        return orderRepository.save(orderToPersist);
    }

    private OrderCreateEvent extractOrderFrom(final InitializeOrderCommand command) {
        checkOrganisation(command.organisationId());
        Cart cart = cartDataMapper.initalizeCartCommandToCart(command);
        return orderDomainService.validateAndInitiateOrder(cart);
    }

    private void checkOrganisation(final OrganisationId organisationId) {
        organisationRepository.findById(organisationId)
                .orElseThrow(() -> {
                    throw new DomainException("Organisation with given id doesn't exist " + organisationId);
                });
    }
}
