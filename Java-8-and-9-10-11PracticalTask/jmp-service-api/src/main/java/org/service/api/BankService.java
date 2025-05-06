package org.service.api;


import org.dto.api.domain.BankCard;
import org.dto.api.domain.Subscription;
import org.dto.api.domain.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public interface BankService {
    void subscribe(BankCard bankCard);
    Optional<Subscription> getSubscriptionByBankCardNumber(String bankcardNumber);
    List<User> getAllUsers();
    default double getAverageUsersAge() {
        return getAllUsers().stream()
                .mapToLong(user -> ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now()))
                .average()
                .orElse(0);
    }


}
