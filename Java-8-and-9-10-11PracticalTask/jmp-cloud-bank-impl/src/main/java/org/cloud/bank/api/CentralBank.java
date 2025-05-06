package org.cloud.bank.api;


import org.bank.api.Bank;
import org.bank.api.BankCardFactory;
import org.dto.api.domain.*;

import java.util.HashMap;
import java.util.Map;

import static org.cloud.bank.api.BankUtil.generateCardNumber;

public class CentralBank implements Bank {
    private static final Map<BankCardType, BankCardFactory> cardFactoryMap = new HashMap<>();

    static {
        cardFactoryMap.put(BankCardType.CREDIT, CreditBankCard::new);
        cardFactoryMap.put(BankCardType.DEBIT, DebitBankCard::new);
    }

    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {
        System.out.println("Service invoked in CentralBank");
        BankCardFactory bankCardFactory = cardFactoryMap.get(cardType);
        String cardNumber = generateCardNumber();

        if (bankCardFactory != null) {
            return bankCardFactory.create(cardNumber, user);
        }
        throw new IllegalArgumentException("Unknown BankCardType: " + cardType);
    }

}
