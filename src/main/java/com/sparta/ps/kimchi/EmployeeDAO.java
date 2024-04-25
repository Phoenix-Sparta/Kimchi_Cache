package com.sparta.ps.kimchi;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.logging.Logger;

public class EmployeeDAO {

    static final Logger LOGGER = Logger.getLogger(EmployeeDAO.class.getName());
    private static ArrayList<Employee> employees;
    private static ArrayList<Employee> employeesByAge;
    private static ArrayList<Employee> employeesByJoinDate;
    private static ArrayList<Employee> employeesBySalary;
    private static Hashtable<Integer, Employee> employeeID = new Hashtable<>();

    private static int numOfEmployees;

    private static EmployeeDAO employeeDAO;

    private EmployeeDAO(ArrayList<Employee> employees) throws IOException{}

    public static void employeeDAOSetUp(ArrayList<Employee> newEmployees) throws IOException {
        employees = employeesByAge = employeesByJoinDate = employeesBySalary = newEmployees;
        numOfEmployees = employees.size();
        EmployeeLogger.configureLogger(LOGGER);

        employeesByAge.sort(Comparator.comparingInt(Employee::age));
        employeesByJoinDate.sort(Comparator.comparing(Employee::dateOfJoin));
        employeesBySalary.sort(Comparator.comparingInt(Employee::salary));

        for(Employee employee : employees){
            employeeID.put(employee.empID(), employee);
        }
    }

    public static EmployeeDAO getEmployeeDAO(){
        return employeeDAO;
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

    public static void addEmployee(Employee employee){
        employees.add(employee);
        employeeID.put(employee.empID(), employee);

        employeesByAge.add(employee);
        employeesByJoinDate.add(employee);
        employeesBySalary.add(employee);

        employeesByAge.sort(Comparator.comparingInt(Employee::age));
        employeesByJoinDate.sort(Comparator.comparing(Employee::dateOfJoin));
        employeesBySalary.sort(Comparator.comparingInt(Employee::salary));
        LOGGER.info("Employee added: " + employee);
    }

    public static String readEmployee(int id){
        LOGGER.info("Employee read with ID " + id);
        return employeeID.get(id).toString();
    }

    public static void deleteEmployee(Employee employee){
        employees.remove(employee);
        employeeID.remove(employee.empID());
        
        employeesByAge.remove(employee);
        employeesByJoinDate.remove(employee);
        employeesBySalary.remove(employee);
        LOGGER.info("Employee deleted: " + employee);
    }

}
