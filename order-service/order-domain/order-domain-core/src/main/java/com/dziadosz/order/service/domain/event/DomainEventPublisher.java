package com.dziadosz.order.service.domain.event;

import com.dziadosz.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {
    void publish(T domainEvent);
}
