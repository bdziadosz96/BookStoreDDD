package com.dziadosz.order.service.domain;

import com.dziadosz.order.service.domain.ports.out.message.publisher.payment.PaymentRequestMessagePublisher;
import com.dziadosz.order.service.domain.ports.out.repository.OrderRepository;
import com.dziadosz.order.service.domain.ports.out.repository.OrganisationRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.dziadosz.order")
public class TestConfig {

    @Bean
    OrganisationRepository organisationRepository() {
        return Mockito.mock(OrganisationRepository.class);
    }

    @Bean
    OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }

    @Bean
    OrderRepository orderRepository() {
        return Mockito.mock(OrderRepository.class);
    }

    @Bean
    PaymentRequestMessagePublisher paymentRequestMessagePublisher() {
        return Mockito.mock(PaymentRequestMessagePublisher.class);
    }
}
