package com.dziadosz.domain.valueobject;

import java.util.UUID;

public class CartId extends BaseId<UUID> {
    public CartId(final UUID value) {
        super(value);
    }
}
