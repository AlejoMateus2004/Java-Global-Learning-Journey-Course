package org.tasks;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

public class EmployeeController {
    private final EmployeeService employeeService = new EmployeeService();

    public void fetchAndPrintHiredEmployeesWithSalaries() {
        // Fetch hired employees asynchronously
        CompletionStage<List<Employee>> hiredEmployeesStage = employeeService.getHiredEmployees();

        // Combine the fetching of employees and their salaries
        CompletionStage<List<Employee>> employeesWithSalariesStage = hiredEmployeesStage.thenCompose(hiredEmployees -> {
            List<CompletableFuture<Employee>> employeeFutures = hiredEmployees.stream()
                    .map(employee -> employeeService.getSalary(employee.getId())
                            .thenApply(salary -> {
                                employee.setSalary(salary);
                                return employee;
                            })
                            .toCompletableFuture())
                    .collect(Collectors.toList());

            CompletableFuture<Void> allOf = CompletableFuture.allOf(employeeFutures.toArray(new CompletableFuture[0]));

            // Combine all the CompletionStages into a single CompletionStage
            return allOf.thenApply(v -> employeeFutures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList()));
        });

        // Print hired employees with their salaries
        employeesWithSalariesStage.thenAccept(employees -> employees.forEach(System.out::println)).toCompletableFuture().join(); // Optionally block the main thread until the operation completes
    }

    public static void main(String[] args) {
        new EmployeeController().fetchAndPrintHiredEmployeesWithSalaries();
    }
}