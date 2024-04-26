package com.sparta.ps.kimchi;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class EmployeeFactoryTest {
    private static EmployeeDAO employeeDAO;
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


        employees = new ArrayList<Employee>();
        employees.add(employee);
        employees.add(employee1);

        employeeDAO = new EmployeeDAO(employees);
    }

    @Test
    @DisplayName("Test that employee ID returns correct result")
    void testThatEmployeeIdReturnsCorrectResult() {
        // Arrange
        // Act
        Employee retrievedEmployee = employeeDAO.getEmployeeByID(123);

        // Assert
        Assertions.assertEquals(employee.empID(), retrievedEmployee.empID(), "Retrieved employee should match the original employee");


    }

    @Test
    @DisplayName("Test that the employee last name works")
    void testThatTheEmployeeLastNameWorks() {
        // Arrange

        // Act
        List<Employee> retrievedEmployee = employeeDAO.getEmployeeByLastNamePartial("Ward");
        List<Employee> retrievedEmployee2 = employeeDAO.getEmployeeByLastNamePartial("ard");
        // Assert
        Assertions.assertEquals(employee.lastName(), retrievedEmployee.get(0).lastName(), "Retried employee should match original employee");
        Assertions.assertEquals(employee.lastName(), retrievedEmployee2.get(0).lastName(), "Retried employee should match original employee");

    }

    @Test
    @DisplayName("Test that the date range search works correctly")
    void testThatTheDateRangeSearchWorksCorrectly() {
        // Arrange

        // Act
        ArrayList<Employee> retrievedEmployee = employeeDAO.getEmployeesHiredWithinDateRange(LocalDate.of(2024, 4, 7 ), LocalDate.of(2024, 4, 9));
        // Assert
        Assertions.assertEquals(2, retrievedEmployee.size(), "Number of retrieved employees should match the expected number of employees");
        Assertions.assertEquals(retrievedEmployee, employees,  "Retrieved employees should match the expected employees");

    }

    @Test
    @DisplayName("Test that getEmployeesHiredDate gets all employees who was hired on that specific date")
    void testThatGetEmployeesHiredDateGetsAllEmployeesWhoWasHiredOnThatSpecificDate() {
        // Arrange

        // Act
        ArrayList<Employee> retrievedEmployee = employeeDAO.getEmployeesHiredDate(LocalDate.of(2024,4,8));

        // Assert
        Assertions.assertEquals(2, retrievedEmployee.size(), "Number of retrieved employees should match the expected number of employees");
    }


    @Test
    @DisplayName("Test that no employees are returned if there are no employees within the age range")
    void testNoEmployeesWithinAgeRange() {
        // Act
        List<Employee> retrievedEmployees = employeeDAO.getEmployeesWithinAgeRange(40, 50);

        // Assert
        Assertions.assertTrue(retrievedEmployees.isEmpty());
    }

    @Test
    @DisplayName("Test that no employees are returned if the start age is greater than the end age")
    void testStartAgeGreaterThanEndAge() {
        // Act
        List<Employee> retrievedEmployees = employeeDAO.getEmployeesWithinAgeRange(35, 25);

        // Assert
        Assertions.assertTrue(retrievedEmployees.isEmpty());
    }

    @Test
    @DisplayName("Test that the correct employee is returned from the correct age range")
    void testCorrectAgeRangeReturnsCorrectEmployee() {
        // Act
        List<Employee> retrievedEmployees = employeeDAO.getEmployeesWithinAgeRange(22, 31);

        // Assert
        Assertions.assertEquals(employees, retrievedEmployees);
    }

    @Test
    @DisplayName("Test that the correct employee is returned from correct age")
    void testThatTheCorrectEmployeeIsReturnedFromCorrectAge() {
        // Arrange

        // Act
        ArrayList<Employee> retrievedEmployees = employeeDAO.getEmployeesWithAge(24);

        // Assert
        Assertions.assertEquals(1,retrievedEmployees.size());
        Assertions.assertEquals(employee, retrievedEmployees.getFirst());

    }


    @Test
    @DisplayName("Test that no employees are returned if there are no employees within the salary range")
    void testNoEmployeesWithinSalaryRange() {
        // Act
        List<Employee> retrievedEmployees = employeeDAO.getEmployeesWithinSalaryRange(110000, 120000);

        // Assert
        Assertions.assertTrue(retrievedEmployees.isEmpty());
    }

    @Test
    @DisplayName("Test that no employees are returned if the start salary is greater than the end salary")
    void testStartSalaryGreaterThanEndSalary() {
        // Act
        List<Employee> retrievedEmployees = employeeDAO.getEmployeesWithinSalaryRange(200000, 90000);

        // Assert
        Assertions.assertTrue(retrievedEmployees.isEmpty());
    }

    @Test
    @DisplayName("Test that the correct employee is returned when given a valid salary range")
    void testSalaryRangeGivesCorrectEmployee() {
        // Act
        List<Employee> retrievedEmployees = employeeDAO.getEmployeesWithinSalaryRange(50000, 200000);

        // Assert
        Assertions.assertEquals(2, retrievedEmployees.size());

    }

    @Test
    @DisplayName("Test that employees with the specified gender are returned")
    void testEmployeesWithSpecifiedGender() {
        // Act
        List<Employee> retrievedEmployees = employeeDAO.getEmployeeByGender('M');

        // Assert
        Assertions.assertTrue(retrievedEmployees.contains(employee));
    }

    @Test
    @DisplayName("Test that employees with the incorrect gender are not returned")
    void testEmployeesWithIncorrectGender() {
        // Act
        List<Employee> retrievedEmployees = employeeDAO.getEmployeeByGender('F');

        // Assert
        Assertions.assertFalse(retrievedEmployees.contains(employee));
    }

    @Test
    @DisplayName("Test that employees are correctly converted to an ArrayList")
    void testConvertEmployeesToArray() {
        // Arrange
        Employee notParsedEmployee = new Employee(123, "mr", "John", 'D',
                "Doe", 'M', "john@example.com", LocalDate.of(1988, 7, 21),
                LocalDate.of(2016, 9, 25), 100000, 36);

        String employeeRecords = "123,mr,John,D,Doe,M,john@example.com,7/21/1988,9/25/2016,100000";

        Employee paresedEmployee = EmployeeParser.parseEmployeeRecord(employeeRecords, DateTimeFormatter.ofPattern("M/d/yyyy"));


        // Assert
        Assertions.assertEquals(notParsedEmployee, paresedEmployee, "These should be the same");
    }

    @Test
    @DisplayName("Check that Covert Employee class functions correctly")
    void checkThatCovertEmployeeClassFunctionsCorrectly() {
        // Arrange

        // Act
        ArrayList<Employee> converted = ConvertEmployeeToArray.convertEmployeesToArray(2);
        // Assert
        Assertions.assertEquals(2,converted.size() );
    }

//    @Test
//    @DisplayName("Check that the employee add methods are working correctly")
//    void checkThatTheEmployeeAddMethodsAreWorkingCorrectly() throws IOException {
//        // Arrange
//        EmployeeDAO tempEmployeeDAO;
//        tempEmployeeDAO = new EmployeeDAO(new ArrayList<>());
//        ArrayList<Employee> tempArrayList = new ArrayList<>();
//
//
//        Employee tempEmp = new Employee(301629, "Mr.","Ruben",'L',"Weissman",'M',"ruben.weissman@gmail.com",
//                LocalDate.of(1995,12,28), LocalDate.of(2017, 1, 3),48543, 29);
//        Employee tempEmpArray = new Employee(368234,"Drs.","Gillian",'T',"Winter",'F',"gillian.winter@gmail.com",
//                LocalDate.of(1960, 1, 17), LocalDate.of(1984, 11, 28),103619, 64);
//        Employee tempEmpString = new Employee(744723,"Hon.","Bibi",'H',"Paddock",'F',"bibi.paddock@yahoo.co.in",
//                LocalDate.of(1991, 10, 20), LocalDate.of(2016, 11, 2),87148, 33);
//
//        tempArrayList.add(tempEmpArray);
//
//        tempEmployeeDAO.addEmployee("744723,Hon.,Bibi,H,Paddock,F,bibi.paddock@yahoo.co.in,10/20/1991,11/02/2016,87148");
//        tempEmployeeDAO.addEmployee(tempEmp);
//        tempEmployeeDAO.addEmployee(tempArrayList);
//        // Act
//
//        // Assert
//        Assertions.assertEquals(tempEmployeeDAO.getEmployeeByID(301629), tempEmp);
//        Assertions.assertEquals(tempEmployeeDAO.getEmployeeByID(368234), tempArrayList.get(0));
//        Assertions.assertEquals(tempEmployeeDAO.getEmployeeByID(744723), tempEmpString);
//
//    }

//    @Test
//    @DisplayName("Check that the remove method is working as intended")
//    void checkThatTheRemoveMethodIsWorkingAsIntended() {
//
//        Employee tempEmp = new Employee(301629, "Mr.","Ruben",'L',"Weissman",'M',"ruben.weissman@gmail.com",
//                LocalDate.of(1995,12,28), LocalDate.of(2017, 1, 3),48543, 29);
//        Employee tempEmpArray = new Employee(368234,"Drs.","Gillian",'T',"Winter",'F',"gillian.winter@gmail.com",
//                LocalDate.of(1960, 1, 17), LocalDate.of(1984, 11, 28),103619, 64);
//        Employee tempEmpString = new Employee(744723,"Hon.","Bibi",'H',"Paddock",'F',"bibi.paddock@yahoo.co.in",
//                LocalDate.of(1991, 10, 20), LocalDate.of(2016, 11, 2),87148, 33);
//
//
//        // Act
//        employeeDAO.addEmployee("744723,Hon.,Bibi,H,Paddock,F,bibi.paddock@yahoo.co.in,10/20/1991,11/02/2016,87148");
//        employeeDAO.addEmployee(tempEmp);
//        employeeDAO.addEmployee(tempEmpArray);
//
//        employeeDAO.removeEmployee("744723,Hon.,Bibi,H,Paddock,F,bibi.paddock@yahoo.co.in,10/20/1991,11/02/2016,87148");
//        employeeDAO.removeEmployee(tempEmp);
//        employeeDAO.removeEmployee(tempEmpArray);
//        // Assert
//        Assertions.assertEquals(employeeDAO.getEmployeeByID(301629), null);
//        Assertions.assertEquals(employeeDAO.getEmployeeByID(368234), null);
//        Assertions.assertEquals(employeeDAO.getEmployeeByID(744723), null);
//    }
}


