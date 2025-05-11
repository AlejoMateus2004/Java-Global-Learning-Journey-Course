package org.task5.dao;

import org.task5.domain.Account;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Logger;

public class AccountDAO {
    private static final String FILE_EXTENSION = ".account";
    private static final Logger logger = Logger.getLogger(AccountDAO.class.getName());

    public synchronized void saveAccount(Account account) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(account.getId() + FILE_EXTENSION)))) {
            oos.writeObject(account.getBalances());
            logger.info("Account saved: " + account.getId());
        }
    }

    public synchronized Account loadAccount(String id) throws IOException, ClassNotFoundException {
        File file = new File(id + FILE_EXTENSION);
        if (!file.exists()) {
            throw new FileNotFoundException("Account not found: " + id);
        }

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(file.toPath()))) {
            @SuppressWarnings("unchecked")
            Map<String, BigDecimal> balances = (Map<String, BigDecimal>) ois.readObject();
            Account account = new Account(id);
            for (Map.Entry<String, BigDecimal> entry : balances.entrySet()) {
                account.addCurrency(entry.getKey(), entry.getValue());
            }
            logger.info("Account loaded: " + id);
            return account;
        }
    }
}