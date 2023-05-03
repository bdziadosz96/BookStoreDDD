package com.dziadosz.order.service.domain.dto.command;

import com.dziadosz.domain.valueobject.CartId;
import com.dziadosz.domain.valueobject.Money;
import com.dziadosz.domain.valueobject.OrganisationId;
import com.dziadosz.order.service.domain.valueobject.DeliveryAddress;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record InitializeOrderCommand(
        @NotNull CartId cartId,
        @NotNull Money totalPrice,
        @NotNull List<OrderBook> orderBooks,
        @NotNull DeliveryAddress deliveryAddress,
        @NotNull OrganisationId organisationId

        ) {
}
