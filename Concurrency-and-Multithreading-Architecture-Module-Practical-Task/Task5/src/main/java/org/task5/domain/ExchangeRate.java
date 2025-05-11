package org.task5.domain;

import java.math.BigDecimal;

public class ExchangeRate {
    private String fromCurrency;
    private String toCurrency;
    BigDecimal rate;

    public ExchangeRate(String fromCurrency, String toCurrency, BigDecimal rate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.rate = rate;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }
}