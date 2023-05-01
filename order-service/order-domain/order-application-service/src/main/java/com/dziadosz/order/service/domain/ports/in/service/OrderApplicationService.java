package com.dziadosz.order.service.domain.ports.in.service;

import com.dziadosz.order.service.domain.dto.command.InitializeCartCommand;
import jakarta.validation.Valid;

public interface OrderApplicationService {
    void payOrder(@Valid InitializeCartCommand command);
}
