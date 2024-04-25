package com.sparta.ps.kimchi;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import static com.sparta.ps.kimchi.ConvertEmployeeToArray.convertEmployeesToArray;
import static com.sparta.ps.kimchi.EmployeeDTO.*;

/**
 * Main class used to interact with the EmployeeDAO object
 * ArrayList<Employee> employees = convertEmployeesToArray(Num) Where num is the number of employee generate from
 * employees.csv file in src/main/resources/employees.csv
 * The logger will provide the output of each search in a reasonable format
 * To generate a search, prepend employeeDAO. with method name and give it a valid input
 * Hover over method name for further detail
 * Methods currently implemented:
 *      getEmployeeByID(int id)
 *      getEmployeeByLastNamePartial(String lastName)
 *      getEmployeesHiredWithinDateRange(LocalDate startingDate, LocalDate endingDate)
 *      getEmployeesHiredDate(LocalDate date)
 *      getEmployeesWithinAgeRange(int minAge, int maxAge)
 *      getEmployeesWithAge(int age)
 *      getEmployeesWithinSalaryRange(int minSalary, int maxSalary)
 *      getEmployeeByGender(char gender)
 */
public class App {
    private static Employee employee;
    public static void main(String[] args) throws IOException {
        ArrayList<Employee> employees = convertEmployeesToArray(10);
        EmployeeDAO employeeDAO = new EmployeeDAO(employees);

        //employeeDAO.getEmployeeByLastNamePartial("an");
        employeeDAO.getEmployeesHiredWithinDateRange(LocalDate.of(2015,1,1), LocalDate.of(2018,1,1));

    }
}
