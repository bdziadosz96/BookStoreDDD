package com.dziadosz.order.service.domain;

import com.dziadosz.order.service.domain.dto.command.InitializeOrderCommand;
import com.dziadosz.order.service.domain.dto.response.InitializeOrderResponse;
import com.dziadosz.order.service.domain.ports.in.service.CartApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class CartApplicationServiceImpl implements CartApplicationService {
    private final InitializeOrderCommandHandler initializeCartCommandHandler;

    public CartApplicationServiceImpl(final InitializeOrderCommandHandler initializeCartCommandHandler) {
        this.initializeCartCommandHandler = initializeCartCommandHandler;
    }

    @Override
    public InitializeOrderResponse initalizeCart(final InitializeOrderCommand command) {
        return initializeCartCommandHandler.initalizeOrder(command);
    }

}
