package com.sparta.ps.kimchi;


import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class EmployeeParser {

    private static final Logger LOGGER = Logger.getLogger(EmployeeParser.class.getName());

    public static Employee parseEmployeeRecord(String employeeRecord, DateTimeFormatter formatter) {
        String[] parts = employeeRecord.split(",");
        Employee employee = new Employee(
                Integer.parseInt(parts[0]),                  // empID
                parts[1],                                    // prefix
                parts[2],                                    // firstName
                parts[3].charAt(0),                          // middleInitial
                parts[4],                                    // lastName
                parts[5].charAt(0),                          // gender
                parts[6],                                    // email
                LocalDate.parse(parts[7], formatter),        // dateOfBirth
                LocalDate.parse(parts[8], formatter),        // dateOfJoin
                Integer.parseInt(parts[9]),                  // salary
                Period.between(LocalDate.parse(parts[7], formatter), LocalDate.now()).getYears() // Age
        );
        LOGGER.config("Employee parsed: " + employee);
        return employee;
    }
}