package com.dziadosz.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Money {
    private final BigDecimal amount;

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money(final BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isGreaterThanZero() {
        return this.amount != null && this.amount.compareTo(Money.ZERO.getAmount()) > 0;
    }

    public boolean isGreaterThan(Money money) {
        return this.amount != null && this.amount.compareTo(money.getAmount()) > 0;
    }

    public Money add(Money money) {
        return new Money(round(this.amount.add(money.getAmount())));
    }

    public Money multiply(Money money) {
        return new Money(round(this.amount.multiply(money.getAmount())));
    }

    public Money multiply(BigDecimal integer) {
        return new Money(round(this.amount.multiply(integer)));
    }

    public boolean isEqualTo(final Money totalPrice) {
        return amount.compareTo(totalPrice.getAmount()) == 0;
    }

    public BigDecimal round(BigDecimal input) {
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }

    BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
