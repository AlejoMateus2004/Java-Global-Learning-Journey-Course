package org.cloud.bank.api;


import org.dto.api.domain.BankCard;
import org.dto.api.domain.User;

public class CentralBank {
    public static class CreditBankCard extends BankCard {
        public CreditBankCard(String number, User user) {
            super(number, user);
        }
    }

    public static class DebitBankCard extends BankCard {
        public DebitBankCard(String number, User user) {
            super(number, user);
        }
    }
}
