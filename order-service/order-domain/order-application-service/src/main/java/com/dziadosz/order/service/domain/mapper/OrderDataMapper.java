package com.dziadosz.order.service.domain.mapper;

import com.dziadosz.domain.annotations.Mapper;
import com.dziadosz.order.service.domain.dto.response.InitializeOrderResponse;
import com.dziadosz.order.service.domain.entity.Order;

@Mapper
public class OrderDataMapper {

    public InitializeOrderResponse orderToInitializeOrderResponse(final Order order) {
        return new InitializeOrderResponse(order.getId());
    }
}
