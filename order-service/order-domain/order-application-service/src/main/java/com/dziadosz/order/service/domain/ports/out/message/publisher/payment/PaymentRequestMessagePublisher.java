package com.dziadosz.order.service.domain.ports.out.message.publisher.payment;

import com.dziadosz.order.service.domain.event.DomainEventPublisher;
import com.dziadosz.order.service.domain.event.order.OrderCreateEvent;

public interface PaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreateEvent> {
}
