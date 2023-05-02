package com.dziadosz.order.service.domain.ports.in.service;

import com.dziadosz.order.service.domain.dto.command.InitializeOrderCommand;
import com.dziadosz.order.service.domain.dto.response.InitializeOrderResponse;
import jakarta.validation.Valid;

public interface CartApplicationService {
    InitializeOrderResponse initalizeCart(@Valid InitializeOrderCommand command);
}
