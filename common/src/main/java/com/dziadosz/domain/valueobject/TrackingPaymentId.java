package com.dziadosz.domain.valueobject;

import java.util.UUID;

public class TrackingPaymentId extends BaseId<UUID> {
    protected TrackingPaymentId(final UUID value) {
        super(value);
    }
}
