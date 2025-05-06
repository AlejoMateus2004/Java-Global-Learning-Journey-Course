package org.dto.api.domain;

import lombok.Data;

import java.time.LocalDate;
@Data
public class Subscription {
    private String bankcardNumber;
    private LocalDate startDate;

}
