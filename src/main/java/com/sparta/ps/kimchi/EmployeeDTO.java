package com.sparta.ps.kimchi;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class EmployeeDTO {

    static final Logger LOGGER = Logger.getLogger(EmployeeDTO.class.getName());
    private static ArrayList<Employee> employees;
    private static Hashtable<Integer, Employee> employeeID = new Hashtable<>();

    private static ArrayList<Employee> employeesByAge;
    private static ArrayList<Employee> employeesByJoinDate;
    private static ArrayList<Employee> employeesBySalary;


    private static int numOfEmployees;

    private EmployeeDTO() throws IOException {}

    public static void employeeDAOSetUp(ArrayList<Employee> newEmployees){
        employees = employeesByAge = employeesByJoinDate = employeesBySalary = newEmployees;
        numOfEmployees = employees.size();

        employeesByAge.sort(Comparator.comparingInt(Employee::age));
        employeesByJoinDate.sort(Comparator.comparing(Employee::dateOfJoin));
        employeesBySalary.sort(Comparator.comparingInt(Employee::salary));

        for(Employee employee : employees){
            employeeID.put(employee.empID(), employee);
        }
    }

    public static ArrayList<Employee> getEmployees() {
        return employees;
    }

    public static ArrayList<Employee> getEmployeesByAge() {
        return employeesByAge;
    }

    public static ArrayList<Employee> getEmployeesByJoinDate() {
        return employeesByJoinDate;
    }

    public static ArrayList<Employee> getEmployeesBySalary() {
        return employeesBySalary;
    }

    public static Hashtable<Integer, Employee> getEmployeeID() {
        return employeeID;
    }

    public static int getNumOfEmployees() {
        return numOfEmployees;
    }

}
