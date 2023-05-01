package com.dziadosz.order.service.domain.ports.out.message.publisher.organisation;

import com.dziadosz.order.service.domain.event.DomainEventPublisher;
import com.dziadosz.order.service.domain.event.order.OrderCreateEvent;

public interface OrganisationApprovalMessagePublisher extends DomainEventPublisher<OrderCreateEvent> {
}
