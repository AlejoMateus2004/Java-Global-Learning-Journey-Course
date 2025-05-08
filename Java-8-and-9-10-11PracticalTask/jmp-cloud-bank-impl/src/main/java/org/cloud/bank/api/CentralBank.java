package org.cloud.bank.api;


import org.bank.api.Bank;
import org.bank.api.BankCardFactory;
import org.dto.api.domain.*;

import static org.cloud.bank.api.BankUtil.generateCardNumber;

public class CentralBank implements Bank {

    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {
        System.out.println("Service invoked in CentralBank");
        BankCardFactory bankCardFactory = BankUtil.getBanCardFactoryMap().get(cardType);
        String cardNumber = generateCardNumber();

        if (bankCardFactory != null) {
            return bankCardFactory.create(cardNumber, user);
        }
        throw new IllegalArgumentException("Unknown BankCardType: " + cardType);
    }

}
