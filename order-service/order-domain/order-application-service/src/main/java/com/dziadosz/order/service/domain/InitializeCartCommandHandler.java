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
import com.dziadosz.order.service.domain.ports.out.repository.OrderRepository;
import com.dziadosz.order.service.domain.ports.out.repository.OrganisationRepository;

@CommandHandler
public class InitializeCartCommandHandler {
    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final OrganisationRepository organisationRepository;
    private final CartDataMapper cartDataMapper;

    InitializeCartCommandHandler(final OrderDomainService orderDomainService,
                                 final OrderRepository orderRepository,
                                 final OrganisationRepository organisationRepository,
                                 final CartDataMapper cartDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.organisationRepository = organisationRepository;
        this.cartDataMapper = cartDataMapper;
    }

    InitializeOrderResponse initalizeCart(final InitializeOrderCommand command) {
        checkOrganisation(command.organisationId());
        Cart cart = cartDataMapper.initalizeCartCommandToCart(command);
        OrderCreateEvent orderCreateEvent = orderDomainService.validateAndInitiateOrder(cart);
        Order extractedOrder = orderCreateEvent.getOrder();
        Order persistedOrder = orderRepository.save(extractedOrder);
        return new InitializeOrderResponse(persistedOrder.getId());
    }

    private void checkOrganisation(final OrganisationId organisationId) {
        organisationRepository.findById(organisationId)
                .orElseThrow(() -> {
                    throw new DomainException("Organisation with given id doesn't exist " + organisationId);
                });
    }
}
