package com.dziadosz.domain.valueobject;

import java.util.UUID;

public class OrderId extends BaseId<UUID> {
    public OrderId(final UUID value) {
        super(value);
    }
}
