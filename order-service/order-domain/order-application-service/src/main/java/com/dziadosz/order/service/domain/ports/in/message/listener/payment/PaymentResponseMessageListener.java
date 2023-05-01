package com.dziadosz.order.service.domain.ports.in.message.listener.payment;

public interface PaymentResponseMessageListener {
    void onPaymentCompleted();
    void onPaymentFailure();
}
