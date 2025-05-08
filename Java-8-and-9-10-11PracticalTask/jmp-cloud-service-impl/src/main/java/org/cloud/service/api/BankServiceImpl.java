package org.cloud.service.api;


import org.dto.api.domain.BankCard;
import org.dto.api.domain.Subscription;
import org.dto.api.domain.User;
import org.service.api.BankService;
import org.service.api.SubscriptionNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BankServiceImpl implements BankService {

    private List<Subscription> subscriptions = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    @Override
    public void subscribe(BankCard bankCard) {
        Subscription subscription = new Subscription();
        subscription.setBankcardNumber(bankCard.getNumber());
        subscription.setStartDate(LocalDate.now());

        subscriptions.add(subscription);

        boolean userExists = users.stream()
                .anyMatch(user -> user.equals(bankCard.getUser()));

        if (!userExists) {
            users.add(bankCard.getUser());
        }
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String bankcardNumber) {
        return Optional.ofNullable(subscriptions.stream()
                .filter(subscription -> Objects.equals(subscription.getBankcardNumber(),bankcardNumber))
                .findFirst()
                .orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found for bankcard number: " + bankcardNumber)));
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> predicate) {
        return subscriptions.stream()
                .filter(predicate)
                .collect(Collectors.toUnmodifiableList());
    }
}
