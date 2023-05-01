package com.dziadosz.order.service.domain.dto.command;

import com.dziadosz.domain.valueobject.Money;
import com.dziadosz.order.service.domain.valueobject.DeliveryAddress;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record InitializeCartCommand(
        @NotNull Money totalPrice,
        @NotNull List<OrderBook> books,
        @NotNull DeliveryAddress deliveryAddress

) {
}
