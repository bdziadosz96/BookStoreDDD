package com.dziadosz.order.service.domain.ports.in.service;

import com.dziadosz.order.service.domain.dto.command.InitializeCartCommand;
import com.dziadosz.order.service.domain.dto.response.InitializeCartResponse;
import jakarta.validation.Valid;

public interface CartApplicationService {
    InitializeCartResponse initalizeCart(@Valid InitializeCartCommand command);
}
