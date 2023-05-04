package com.dziadosz.domain.valueobject;

import java.util.UUID;

public class OrganisationId extends BaseId<UUID> {
    public OrganisationId(final UUID value) {
        super(value);
    }
}
