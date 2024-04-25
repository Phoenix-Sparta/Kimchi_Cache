package com.sparta.ps.kimchi;


import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class EmployeeParser {

    private static final Logger LOGGER = Logger.getLogger(ConvertEmployeeToArray.class.getName());

    static {
        try {
            EmployeeLogger.configureLogger(LOGGER);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public static Employee parseEmployeeRecord(String employeeRecord, DateTimeFormatter formatter) {
        String[] parts = employeeRecord.split(",");
        if (parts.length < 10) {
            LOGGER.warning("Invalid employee record: Insufficient data");
            return null; // or throw an exception as per your requirement
        }

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
        LOGGER.info("Employee parsed: " + employee);
        return employee;
    }
}