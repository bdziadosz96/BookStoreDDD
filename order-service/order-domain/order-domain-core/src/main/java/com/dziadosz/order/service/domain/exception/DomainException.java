package com.dziadosz.order.service.domain.exception;

public class DomainException extends RuntimeException{
    public DomainException(final String message) {
        super(message);
    }
}
