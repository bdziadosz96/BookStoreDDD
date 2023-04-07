package com.dziadosz.order.service.domain.entity;

import com.dziadosz.domain.entity.AggregateRoot;
import com.dziadosz.domain.valueobject.Money;
import com.dziadosz.domain.valueobject.OrganisationId;
import java.util.List;

public class Organisation extends AggregateRoot<OrganisationId> {
    private final List<OrderBook> books;
    private final Money price;
    private boolean active;

    private Organisation(final Builder builder) {
        super.setId(builder.organisationId);
        books = builder.books;
        price = builder.price;
        active = builder.active;
    }


    public static final class Builder {
        private OrganisationId organisationId;
        private List<OrderBook> books;
        private Money price;
        private boolean active;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(final OrganisationId val) {
            organisationId = val;
            return this;
        }

        public Builder books(final List<OrderBook> val) {
            books = val;
            return this;
        }

        public Builder price(final Money val) {
            price = val;
            return this;
        }

        public Builder active(final boolean val) {
            active = val;
            return this;
        }

        public Organisation build() {
            return new Organisation(this);
        }
    }
}
