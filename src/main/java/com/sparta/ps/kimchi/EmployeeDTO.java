package com.sparta.ps.kimchi;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class EmployeeDTO {

    static final Logger LOGGER = Logger.getLogger(EmployeeDTO.class.getName());
    private static final ArrayList<Employee> employees = new ArrayList<>();
    private static final Hashtable<Integer, Employee> employeeID = new Hashtable<>();

    private static final ArrayList<Employee> employeesByAge= new ArrayList<>();
    private static final ArrayList<Employee> employeesByJoinDate= new ArrayList<>();
    private static final ArrayList<Employee> employeesBySalary= new ArrayList<>();


    private static int numOfEmployees;

    private EmployeeDTO() throws IOException {}

    public static void employeeDAOSetUp(ArrayList<Employee> newEmployees){
        // Deep copy each ArrayList
        for(Employee employee : newEmployees){
            employees.add(employee);
            employeesByAge.add(employee);
            employeesByJoinDate.add(employee);
            employeesBySalary.add(employee);
        }

        numOfEmployees = employees.size();
        // Sort all ArrayLists based on their own comparator
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
