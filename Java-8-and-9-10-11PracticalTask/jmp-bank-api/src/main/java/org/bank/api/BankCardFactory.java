package org.bank.api;

import org.dto.api.domain.BankCard;
import org.dto.api.domain.User;

@FunctionalInterface
public interface BankCardFactory {
    BankCard create(String number, User user);
}
