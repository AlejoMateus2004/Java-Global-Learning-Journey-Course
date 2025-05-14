package org.task5;

import org.task5.dao.AccountDAO;
import org.task5.domain.Account;
import org.task5.domain.ExchangeRate;
import org.task5.service.ExchangeService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class App
{
    private static final Logger logger = Logger.getLogger(App.class.getName());
    public static void main(String[] args) throws Exception {
        List<ExchangeRate> rates = Arrays.asList(
                new ExchangeRate("USD", "EUR", new BigDecimal("0.85")),
                new ExchangeRate("EUR", "USD", new BigDecimal("1.18")),
                new ExchangeRate("USD", "JPY", new BigDecimal("110.45")),
                new ExchangeRate("JPY", "USD", new BigDecimal("0.0091"))
        );

        AccountDAO accountDAO = new AccountDAO();
        ExchangeService exchangeService = new ExchangeService(rates, accountDAO);

        Account account = new Account("user1");
        account.addCurrency("USD", new BigDecimal("1000"));
        account.addCurrency("EUR", new BigDecimal("500"));
        accountDAO.saveAccount(account);

        logger.info("Initial user account balances:");
        Account loadedAccount = accountDAO.loadAccount("user1");
        for (String currency : loadedAccount.getBalances().keySet()) {
            logger.info(currency + ": " + loadedAccount.getBalance(currency));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Runnable task1 = () -> {
            try {
                exchangeService.exchangeCurrency("user1", "USD", "EUR", new BigDecimal("100"));
            } catch (Exception e) {
                logger.severe("Task1: " + e.getMessage());
            }
        };

        Runnable task2 = () -> {
            try {
                exchangeService.exchangeCurrency("user1", "EUR", "USD", new BigDecimal("50"));
            } catch (Exception e) {
                logger.severe("Task2: " + e.getMessage());
            }
        };

        Runnable task3 = () -> {
            try {
                exchangeService.exchangeCurrency("user1", "USD", "JPY", new BigDecimal("200"));
            } catch (Exception e) {
                logger.severe("Task3: " + e.getMessage());
            }
        };

        executorService.submit(task1);
        executorService.submit(task2);
        executorService.submit(task3);

        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }

        logger.info("Final user account balances:");
        loadedAccount = accountDAO.loadAccount("user1");
        for (String currency : loadedAccount.getBalances().keySet()) {
            logger.info(currency + ": " + loadedAccount.getBalance(currency));
        }
    }
}
