package com.sparta.ps.kimchi;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import static com.sparta.ps.kimchi.ConvertEmployeeToArray.convertEmployeesToArray;

public class App {
    private static Employee employee;
    public static void main(String[] args) throws IOException {
        ArrayList<Employee> employees = convertEmployeesToArray(500);
        EmployeeDAO employeeDAO = new EmployeeDAO(employees);

        System.out.println(employeeDAO.getEmployeeByGender('M').size());

    }
}
