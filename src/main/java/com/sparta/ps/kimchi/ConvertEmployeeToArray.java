package com.sparta.ps.kimchi;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ConvertEmployeeToArray {

    private static final Logger LOGGER = Logger.getLogger(ConvertEmployeeToArray.class.getName());

    public static ArrayList<Employee> convertEmployeesToArray(int i) {
        String[] employeeRecords = EmployeeFactory.getEmployees(i);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        ArrayList<Employee> employees = new ArrayList<>();

        for (String employeeRecord : employeeRecords) {
            Employee employee = EmployeeParser.parseEmployeeRecord(employeeRecord, formatter);
            employees.add(employee);
            LOGGER.info("Employee converted: " + employee);
        }

        return employees;
    }

}


