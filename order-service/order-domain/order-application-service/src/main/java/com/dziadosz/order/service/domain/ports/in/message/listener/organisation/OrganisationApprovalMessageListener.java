package com.dziadosz.order.service.domain.ports.in.message.listener.organisation;

public interface OrganisationApprovalMessageListener {
    void organisationApproved();
    void organisationRejected();
}
