package org.task5.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Account {
    private String id;
    private Map<String, BigDecimal> balances;

    public Account(String id) {
        this.id = id;
        this.balances = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public Map<String, BigDecimal> getBalances() {
        return balances;
    }

    public void addCurrency(String currency, BigDecimal amount) {
        balances.put(currency, amount);
    }

    public BigDecimal getBalance(String currency) {
        return balances.getOrDefault(currency, BigDecimal.ZERO);
    }

    public void setBalance(String currency, BigDecimal amount) {
        balances.put(currency, amount);
    }
}