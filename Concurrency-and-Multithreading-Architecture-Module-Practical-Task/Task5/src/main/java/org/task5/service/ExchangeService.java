package org.task5.service;

import org.task5.dao.AccountDAO;
import org.task5.domain.Account;
import org.task5.domain.ExchangeRate;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

public class ExchangeService {
    private List<ExchangeRate> exchangeRates;
    private AccountDAO accountDAO;
    private static final Logger logger = Logger.getLogger(ExchangeService.class.getName());

    public ExchangeService(List<ExchangeRate> exchangeRates, AccountDAO accountDAO) {
        this.exchangeRates = exchangeRates;
        this.accountDAO = accountDAO;
    }

    public void exchangeCurrency(String accountId, String fromCurrency, String toCurrency, BigDecimal amount) throws Exception {
        Account account = accountDAO.loadAccount(accountId);

        synchronized (account) {
            BigDecimal fromBalance = account.getBalance(fromCurrency);
            if (fromBalance.compareTo(amount) < 0) {
                throw new Exception("Insufficient funds for exchange.");
            }

            ExchangeRate rate = getExchangeRate(fromCurrency, toCurrency);
            if (rate == null) {
                throw new Exception("Exchange rate not found.");
            }
            BigDecimal toAmount = amount.multiply(rate.getRate());

            account.setBalance(fromCurrency, fromBalance.subtract(amount));
            account.setBalance(toCurrency, account.getBalance(toCurrency).add(toAmount));
            logger.info("Executed exchange: " + accountId + " from " + fromCurrency + " to " + toCurrency + " amount: " + amount);
        }

        accountDAO.saveAccount(account);
    }

    private ExchangeRate getExchangeRate(String fromCurrency, String toCurrency) {
        for (ExchangeRate rate : exchangeRates) {
            if (rate.getFromCurrency().equals(fromCurrency) && rate.getToCurrency().equals(toCurrency)) {
                return rate;
            }
        }
        return null;
    }
}