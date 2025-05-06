package org.cloud.bank.api;


import org.bank.api.Bank;
import org.dto.api.domain.BankCard;
import org.dto.api.domain.BankCardType;
import org.dto.api.domain.User;

import java.util.UUID;

public class BankImpl implements Bank {
    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {
        String cardNumber = generateCardNumber();
        switch (cardType) {
            case CREDIT:
                return new RetailBank.CreditBankCard(cardNumber, user);
            case DEBIT:
                return new RetailBank.DebitBankCard(cardNumber, user);
            default:
                throw new IllegalArgumentException("Unknown card type: " + cardType);
        }
    }

    private String generateCardNumber() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
    }
}
