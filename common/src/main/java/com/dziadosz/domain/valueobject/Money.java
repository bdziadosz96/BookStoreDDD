package com.dziadosz.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Money {
    private final BigDecimal amount;
    private final String sign;

    public Money(final BigDecimal amount, final String sign) {
        this.amount = amount;
        this.sign = sign;
    }

    public boolean isPolishZloty() {
        return this.sign.equalsIgnoreCase("zl");
    }

    public boolean isGreaterThanZero() {
        return this.amount != null && this.amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThan(Money money) {
        return this.amount != null && this.amount.compareTo(money.getAmount()) > 0;
    }

    public Money add(Money money) {
        return new Money(round(this.amount.add(money.getAmount())), money.getSign());
    }

    public Money multiply(Money money) {
        return new Money(round(this.amount.multiply(money.getAmount())), money.getSign());
    }

    public BigDecimal round(BigDecimal input) {
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }

    BigDecimal getAmount() {
        return amount;
    }

    String getSign() {
        return sign;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount) && Objects.equals(sign, money.sign);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, sign);
    }
}
