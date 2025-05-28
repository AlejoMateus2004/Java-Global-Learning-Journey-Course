package org.tasks;

import lombok.Data;

import java.util.Optional;

@Data
public class Employee {
    private String id;
    private String name;
    private Optional<Double> salary;

    // Constructor
    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
        this.salary = Optional.empty(); // Initialized to empty
    }
}
