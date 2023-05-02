package com.dziadosz.order.service.domain.ports.out.repository;

import com.dziadosz.domain.valueobject.OrganisationId;
import com.dziadosz.order.service.domain.entity.Organisation;
import java.util.Optional;

public interface OrganisationRepository {
    Optional<Organisation> findById(OrganisationId organisationId);
}
