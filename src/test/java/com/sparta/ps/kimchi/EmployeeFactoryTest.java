package com.sparta.ps.kimchi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class EmployeeFactoryTest {

//    @BeforeAll
//    public static void beforeTests() {
//        Employee employee = new Employee(123, 'm', "Patrick", 'M',
//                "Ward", 'm', "email@email.com", LocalDate.of(1999, 10, 30),
//                LocalDate.of(2024, 4, 8), 100000);
//    }


    @Test
    @DisplayName("Test that employee ID returns correct result")
    void testThatEmployeeIdReturnsCorrectResult() {
        // Arrange
        Employee employee = new Employee(123, 'm', "Patrick", 'M',
                "Ward", 'm', "email@email.com", LocalDate.of(1999, 10, 30),
                LocalDate.of(2024, 4, 8), 100000);
        EmployeeDAO employeeDAO = new EmployeeDAO(new ArrayList<>(List.of(employee)));

        // Act
        Employee retrievedEmployee = employeeDAO.getEmployeeByID(123);

        // Assert
        Assertions.assertEquals(employee.empID(), retrievedEmployee.empID(), "Retrieved employee should match the original employee");


    }

    @Test
    @DisplayName("Test that the employee last name works")
    void testThatTheEmployeeLastNameWorks() {
        // Arrange
        Employee employee = new Employee(123, 'm', "Patrick", 'M',
                "Ward", 'm', "email@email.com", LocalDate.of(1999, 10, 30),
                LocalDate.of(2024, 4, 8), 100000);
        EmployeeDAO employeeDAO = new EmployeeDAO(new ArrayList<>(List.of(employee)));
        // Act
        List<Employee> retrievedEmployee = employeeDAO.getEmployeeByLastNamePartial("Ward");
        List<Employee> retrievedEmployee2 = employeeDAO.getEmployeeByLastNamePartial("ard");
        // Assert
        Assertions.assertEquals(employee.lastName(), retrievedEmployee.get(0).lastName(), "Retried employee should match original employee");
        Assertions.assertEquals(employee.lastName(), retrievedEmployee2.get(0).lastName(), "Retried employee should match original employee");

    }
}
