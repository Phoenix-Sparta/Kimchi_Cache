package com.sparta.ps.kimchi;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class EmployeeFactoryTest {
    private static EmployeeAPIs employeeAPIs;
    private static Employee employee;
    private static Employee employee1;
    private static ArrayList<Employee> employees;

    @BeforeAll
    public static void beforeTests() throws IOException {
        employee = new Employee(123, "mr", "Patrick", 'M',
                "Ward", 'M', "email@email.com", LocalDate.of(1999, 10, 30),
                LocalDate.of(2024, 4, 8), 100000, 24);
        employee1 = new Employee(123, "mr", "Patrick", 'M',
                "Ward", 'M', "email@email.com", LocalDate.of(1999, 10, 30),
                LocalDate.of(2024, 4, 8), 100000, 30);
//      employee2 = new Employee(1123, "mr", "Patrick", 'M',
//                "Ward", 'm', "email@email.com", LocalDate.of(1999, 10, 30),
//                LocalDate.of(2024, 4, 8), 100000, 24);
        employees = new ArrayList<Employee>();
        employees.add(employee);
        employees.add(employee1);

        employeeAPIs = new EmployeeAPIs(employees);
    }

    @Test
    @DisplayName("Test that employee ID returns correct result")
    void testThatEmployeeIdReturnsCorrectResult() {
        // Arrange
        // Act
        Employee retrievedEmployee = employeeAPIs.getEmployeeByID(123);

        // Assert
        Assertions.assertEquals(employee.empID(), retrievedEmployee.empID(), "Retrieved employee should match the original employee");


    }

    @Test
    @DisplayName("Test that the employee last name works")
    void testThatTheEmployeeLastNameWorks() {
        // Arrange

        // Act
        List<Employee> retrievedEmployee = employeeAPIs.getEmployeeByLastNamePartial("Ward");
        List<Employee> retrievedEmployee2 = employeeAPIs.getEmployeeByLastNamePartial("ard");
        // Assert
        Assertions.assertEquals(employee.lastName(), retrievedEmployee.get(0).lastName(), "Retried employee should match original employee");
        Assertions.assertEquals(employee.lastName(), retrievedEmployee2.get(0).lastName(), "Retried employee should match original employee");

    }

    @Test
    @DisplayName("Test that the date range search works correctly")
    void testThatTheDateRangeSearchWorksCorrectly() {
        // Arrange

        // Act
        ArrayList<Employee> retrievedEmployee = employeeAPIs.getEmployeesHiredWithinDateRange(LocalDate.of(2024, 4, 7 ), LocalDate.of(2024, 4, 9));
        // Assert
        Assertions.assertEquals(2, retrievedEmployee.size(), "Number of retrieved employees should match the expected number of employees");
        Assertions.assertEquals(retrievedEmployee, employees,  "Retrieved employees should match the expected employees");

    }


    @Test
    @DisplayName("Test that no employees are returned if there are no employees within the age range")
    void testNoEmployeesWithinAgeRange() {
        // Act
        List<Employee> retrievedEmployees = employeeAPIs.getEmployeesWithinAgeRange(40, 50);

        // Assert
        Assertions.assertTrue(retrievedEmployees.isEmpty());
    }

    @Test
    @DisplayName("Test that no employees are returned if the start age is greater than the end age")
    void testStartAgeGreaterThanEndAge() {
        // Act
        List<Employee> retrievedEmployees = employeeAPIs.getEmployeesWithinAgeRange(35, 25);

        // Assert
        Assertions.assertTrue(retrievedEmployees.isEmpty());
    }


    @Test
    @DisplayName("Test that no employees are returned if there are no employees within the salary range")
    void testNoEmployeesWithinSalaryRange() {
        // Act
        List<Employee> retrievedEmployees = employeeAPIs.getEmployeesWithinSalaryRange(110000, 120000);

        // Assert
        Assertions.assertTrue(retrievedEmployees.isEmpty());
    }

    @Test
    @DisplayName("Test that no employees are returned if the start salary is greater than the end salary")
    void testStartSalaryGreaterThanEndSalary() {
        // Act
        List<Employee> retrievedEmployees = employeeAPIs.getEmployeesWithinSalaryRange(200000, 90000);

        // Assert
        Assertions.assertTrue(retrievedEmployees.isEmpty());
    }

    @Test
    @DisplayName("Test that the correct employee is returned when given a valid salary range")
    void testSalaryRangeGivesCorrectEmployee() {
        // Act
        List<Employee> retrievedEmployees = employeeAPIs.getEmployeesWithinSalaryRange(50000, 200000);

        // Assert
        Assertions.assertEquals(2, retrievedEmployees.size());

    }

    @Test
    @DisplayName("Test that employees with the specified gender are returned")
    void testEmployeesWithSpecifiedGender() {
        // Act
        List<Employee> retrievedEmployees = employeeAPIs.getEmployeeByGender('M');

        // Assert
        Assertions.assertTrue(retrievedEmployees.contains(employee));
    }

    @Test
    @DisplayName("Test that employees with the incorrect gender are not returned")
    void testEmployeesWithIncorrectGender() {
        // Act
        List<Employee> retrievedEmployees = employeeAPIs.getEmployeeByGender('F');

        // Assert
        Assertions.assertFalse(retrievedEmployees.contains(employee));
    }


}

