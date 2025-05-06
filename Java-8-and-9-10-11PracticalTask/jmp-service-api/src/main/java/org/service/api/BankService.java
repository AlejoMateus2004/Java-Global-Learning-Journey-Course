package org.service.api;


import org.dto.api.domain.BankCard;
import org.dto.api.domain.Subscription;
import org.dto.api.domain.User;

import java.util.List;
import java.util.Optional;

public interface BankService {
    void subscribe(BankCard bankCard);
    Optional<Subscription> getSubscriptionByBankCardNumber(String bankcardNumber);
    List<User> getAllUsers();

}
