package com.dziadosz.domain.entity;

import java.util.Objects;

public abstract class BaseEntity<ID> {
    private ID id;

    ID getId() {
        return id;
    }

    void setId(final ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity<?> that = (BaseEntity<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
