package org.cloud.bank.api;

import java.util.UUID;

public class BankUtil {
    static String generateCardNumber() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
    }
}
