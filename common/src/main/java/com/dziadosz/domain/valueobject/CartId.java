package com.dziadosz.domain.valueobject;

import java.util.UUID;

public class CartId extends BaseId<UUID> {
    protected CartId(final UUID value) {
        super(value);
    }
}
