package com.sparta.ps.kimchi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ConvertEmployeeToArray {

    public static ArrayList<Employee> convertEmployeesToArray(int i) {
        String[] employeeRecords = EmployeeFactory.getEmployees(i);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        ArrayList<Employee> employees = new ArrayList<>();

        // Parse each string and create an Employee record
        for (String employeeRecord : employeeRecords) {
            String[] parts = employeeRecord.split(","); // Assuming CSV fields are separated by comma
            Employee employee = new Employee(
                    Integer.parseInt(parts[0]),                  // empID
                    parts[1].charAt(0),                          // prefix
                    parts[2],                                    // firstName
                    parts[3].charAt(0),                          // middleInitial
                    parts[4],                                    // lastName
                    parts[5].charAt(0),                          // gender
                    parts[6],                                    // email
                    LocalDate.parse(parts[7], formatter),        // dateOfBirth
                    LocalDate.parse(parts[8], formatter),        // dateOfJoin
                    Integer.parseInt(parts[9])                   // salary
            );
            employees.add(employee);
        }
        return employees;
    }

}


