package com.dziadosz.order.service.domain.dto.response;

import com.dziadosz.domain.valueobject.CartId;
import com.dziadosz.domain.valueobject.OrderId;

public record InitializeOrderResponse(OrderId orderId) {
}
