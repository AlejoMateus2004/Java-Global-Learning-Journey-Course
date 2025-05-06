package org.bank.api;


import org.dto.api.domain.BankCard;
import org.dto.api.domain.BankCardType;
import org.dto.api.domain.User;

public interface Bank {
    BankCard createBankCard(User user, BankCardType cardType);
}
