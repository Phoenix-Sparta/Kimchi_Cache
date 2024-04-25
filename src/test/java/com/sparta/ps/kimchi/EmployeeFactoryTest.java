package com.sparta.ps.kimchi;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class EmployeeFactoryTest {
    private static EmployeeAPIs employeeAPIs;
    private static Employee employee;
    private static Employee employee2;

    @BeforeAll
    public static void beforeTests() throws IOException {
        employee = new Employee(123, "mr", "Patrick", 'M',
                "Ward", 'm', "email@email.com", LocalDate.of(1999, 10, 30),
                LocalDate.of(2024, 4, 8), 100000, 24);
        employee2 = new Employee(1123, "mr", "Patrick", 'M',
                "Ward", 'm', "email@email.com", LocalDate.of(1999, 10, 30),
                LocalDate.of(2024, 4, 8), 100000, 24);
        employeeAPIs = new EmployeeAPIs(new ArrayList<>(List.of(employee)));
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
        ArrayList<Employee> expectedEmployees = new ArrayList<>();
        expectedEmployees.add(employee);
        // Assert
        Assertions.assertEquals(expectedEmployees.size(), retrievedEmployee.size(), "Number of retrieved employees should match the expected number of employees");
        Assertions.assertTrue(retrievedEmployee.containsAll(expectedEmployees), "Retrieved employees should match the expected employees");

    }

//    @Test
//    @DisplayName("Check that the remove function works")
//    void checkThatTheRemoveFunctionWorks() {
//        // Arrange
//
//        // Act
//        EmployeeAPIs.deleteEmployee(employee2);
//        Employee retrievedEmployee = employeeAPIs.getEmployeeByID(1123);
//
//        // Assert
//        Assertions.assertNull(retrievedEmployee, "Employee should not be found after deletion");
//
//    }
//
//    @Test
//    @DisplayName("Test the read method")
//    void testTheReadMethod() {
//        // Arrange
//
//        // Act
//        String retrievedEmployeeString = employeeAPIs.readEmployee(123);
//
//        // Assert
//        Assertions.assertEquals(retrievedEmployeeString, "Employee{empID=123', prefix=mr, firstName='Patrick', middleInitial=M, lastName='Ward', gender=m, email='email@email.com', dateOfBirth=1999-10-30, dateOfJoin=2024-04-08, salary=100000}");
//    }
}
