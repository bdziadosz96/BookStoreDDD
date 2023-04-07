package com.dziadosz.domain.valueobject;

import java.util.UUID;

public class OrderBookId extends BaseId<UUID> {
    public OrderBookId(final UUID value) {
        super(value);
    }
}
