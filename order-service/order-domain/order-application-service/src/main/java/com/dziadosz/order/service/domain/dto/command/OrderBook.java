package com.dziadosz.order.service.domain.dto.command;

import com.dziadosz.domain.valueobject.Money;
import jakarta.validation.constraints.NotNull;

public record OrderBook(
        @NotNull String name,
        @NotNull String author,
        @NotNull Money price,
        @NotNull Integer quantity
) {
}
