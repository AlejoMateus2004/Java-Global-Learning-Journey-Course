package org.task5.util;

import org.task5.dao.AccountDAO;
import org.task5.domain.Account;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Logger;

public class ValidationUtils {
    private static final Logger logger = Logger.getLogger(ValidationUtils.class.getName());
    public static void validateAccountExists(AccountDAO accountDAO, String accountId) throws Exception {
        try {
            accountDAO.loadAccount(accountId);
        } catch (FileNotFoundException e) {
            logger.warning("Validation failed: Account does not exist: " + accountId);
            throw new Exception("Account does not exist: " + accountId);
        } catch (IOException | ClassNotFoundException e) {
            logger.warning("Validation failed: Error loading account: " + accountId);
            throw new Exception("Error loading account: " + accountId, e);
        }
    }

    public static void validateSufficientFunds(Account account, String currency, BigDecimal amount) throws Exception {
        BigDecimal balance = account.getBalance(currency);
        if (balance.compareTo(amount) < 0) {
            logger.warning("Validation failed: Insufficient funds for currency: " + currency);
            throw new Exception("Insufficient funds for currency: " + currency);
        }
    }
}