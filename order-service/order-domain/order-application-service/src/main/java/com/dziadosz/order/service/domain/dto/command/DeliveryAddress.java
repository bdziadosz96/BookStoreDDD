package com.dziadosz.order.service.domain.dto.command;

public record DeliveryAddress(
        String street,
        String city,
        String postalCode
) {
}
