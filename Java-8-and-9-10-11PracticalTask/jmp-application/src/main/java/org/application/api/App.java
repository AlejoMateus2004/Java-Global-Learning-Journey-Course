package org.application.api;

import org.bank.api.Bank;
import org.dto.api.domain.BankCard;
import org.dto.api.domain.BankCardType;
import org.dto.api.domain.User;
import org.service.api.BankService;

import java.time.LocalDate;
import java.util.ServiceLoader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ServiceLoader<Bank> bankLoader = ServiceLoader.load(Bank.class);
        Bank bank = bankLoader.findFirst().orElseThrow();
        System.out.println("Bank Implementation: " + bank.getClass().getSimpleName());

        ServiceLoader<BankService> serviceLoader = ServiceLoader.load(BankService.class);
        BankService service = serviceLoader.findFirst().orElseThrow();

        User user = new User();
        user.setName("John");
        user.setSurname("Doe");
        user.setBirthday(LocalDate.of(1980, 1, 1));

        BankCard creditBankCard = bank.createBankCard(user, BankCardType.CREDIT);
        service.subscribe(creditBankCard);

        BankCard debitBankCard = bank.createBankCard(user, BankCardType.DEBIT);
        service.subscribe(debitBankCard);

        System.out.println("Users subscribed: ");
        service.getAllUsers().forEach(System.out::println);

        System.out.println("Average user age: " + service.getAverageUsersAge());

        System.out.println("Is user payable: " + BankService.isPayableUser(user));

        System.out.println("Subscriptions started today:");
        service.getAllSubscriptionsByCondition(s -> s.getStartDate().equals(LocalDate.now())).forEach(System.out::println);

        System.out.println("Subscriptions by credit card number:");
        service.getSubscriptionByBankCardNumber(creditBankCard.getNumber()).ifPresentOrElse(
                subscription -> System.out.println("Subscription found: " + subscription),
                () -> System.out.println("Subscription not found")
        );

    }
}
