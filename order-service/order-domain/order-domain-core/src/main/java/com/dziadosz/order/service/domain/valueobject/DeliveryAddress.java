package com.dziadosz.order.service.domain.valueobject;

import java.util.Objects;

public class DeliveryAddress {
    private final String street;
    private final String city;
    private final String postalCode;

    DeliveryAddress(final String street, final String city, final String postalCode) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    String getStreet() {
        return street;
    }

    String getCity() {
        return city;
    }

    String getPostalCode() {
        return postalCode;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryAddress that = (DeliveryAddress) o;
        return Objects.equals(street, that.street) && Objects.equals(city, that.city) && Objects.equals(postalCode, that.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city, postalCode);
    }
}
