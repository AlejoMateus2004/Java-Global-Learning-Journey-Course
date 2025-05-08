package org.dto.api.domain;

import lombok.Data;

@Data
public abstract class BankCard {

    private String number;
    private User user;

    public BankCard(String number, User user) {
        this.number = number;
        this.user = user;
    }
}
