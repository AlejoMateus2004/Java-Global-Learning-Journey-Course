package org.bank.api;

import org.dto.api.domain.BankCard;
import org.dto.api.domain.User;

public interface BankCardFactory {
    BankCard create(String number, User user);
}
