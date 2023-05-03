package com.dziadosz.order.service.domain.exception;

import java.io.Serial;

public class DomainException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public DomainException(final String message) {
        super(message);
    }
}
