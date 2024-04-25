package com.sparta.ps.kimchi;

import java.io.IOException;
import java.util.ArrayList;

import static com.sparta.ps.kimchi.ConvertEmployeeToArray.convertEmployeesToArray;

public class App {
    public static void main(String[] args) throws IOException {
        ArrayList<Employee> employees = convertEmployeesToArray(500);
        EmployeeDAO employeeDAO = new EmployeeDAO(employees);
        employeeDAO.getEmployeeByID(1234);

    }
}
