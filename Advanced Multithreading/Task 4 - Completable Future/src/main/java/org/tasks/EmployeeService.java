package org.tasks;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class EmployeeService {

    public CompletionStage<List<Employee>> getHiredEmployees() {
        return CompletableFuture.supplyAsync(() -> List.of(
                new Employee("1", "Jane Doe"),
                new Employee("2", "John Smith"),
                new Employee("3", "Alice Johnson")
        ));
    }

    public CompletionStage<Optional<Double>>  getSalary(String employeeId) {
        return CompletableFuture.supplyAsync(() ->  Optional.of(Math.random() * 10000 + 50000));
    }
}
