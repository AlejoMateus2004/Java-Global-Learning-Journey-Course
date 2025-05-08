package org.dto.api.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {

    private String name;
    private String surname;
    private LocalDate birthday;

}
