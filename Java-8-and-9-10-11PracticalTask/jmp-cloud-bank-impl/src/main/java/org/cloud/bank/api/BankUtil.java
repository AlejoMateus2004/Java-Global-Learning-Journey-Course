package org.cloud.bank.api;

import org.bank.api.BankCardFactory;
import org.dto.api.domain.BankCardType;
import org.dto.api.domain.CreditBankCard;
import org.dto.api.domain.DebitBankCard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BankUtil {
    private static final Map<BankCardType, BankCardFactory> cardFactoryMap = new HashMap<>();

    static {
        cardFactoryMap.put(BankCardType.CREDIT, CreditBankCard::new);
        cardFactoryMap.put(BankCardType.DEBIT, DebitBankCard::new);
    }

    public static Map<BankCardType, BankCardFactory> getBanCardFactoryMap() {
        return cardFactoryMap;
    }
    static String generateCardNumber() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
    }
}
