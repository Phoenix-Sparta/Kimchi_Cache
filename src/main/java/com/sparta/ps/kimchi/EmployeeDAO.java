package com.sparta.ps.kimchi;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.logging.Logger;

public class EmployeeDAO {

    static final Logger LOGGER = Logger.getLogger(EmployeeDAO.class.getName());
    private ArrayList<Employee> employees;
    private ArrayList<Employee> employeesByAge;
    private Hashtable<Integer, Employee> employeeID = new Hashtable<>();

    public EmployeeDAO(ArrayList<Employee> employees) throws IOException {
        EmployeeLogger.configureLogger(LOGGER);
        this.employees = employees;

        this.employeesByAge = employees;
        employeesByAge.sort(Comparator.comparingInt(Employee::age));

        for(Employee employee : employees){
            employeeID.put(employee.empID(), employee);
        }
    }

    public void addEmployee(Employee employee){
        employees.add(employee);
        employeeID.put(employee.empID(), employee);
        LOGGER.info("Employee added: " + employee);
    }

    public String readEmployee(int id){
        LOGGER.info("Employee read with ID " + id);
        return employeeID.get(id).toString();
    }

    public Hashtable<Integer, Employee> getEmployeeIDHashtable(){
        return employeeID;
    }

    public void deleteEmployee(Employee employee){
        employees.remove(employee);
        employeeID.remove(employee.empID());
        LOGGER.info("Employee deleted: " + employee);
    }

    public Employee getEmployeeByID(int id){
        return employeeID.getOrDefault(id, null);
    }

    public ArrayList<Employee> getEmployeeByLastNamePartial(String lastName){
        ArrayList<Employee> matches = new ArrayList<>();
        for(Employee employee : employees){
            if(employee.lastName().contains(lastName)){
                matches.add(employee);
            }
        }
        LOGGER.info("Employees found by last name partial '" + lastName + "': " + matches);
        return matches;
    }

    public ArrayList<Employee> getEmployeesHiredWithinDateRange(LocalDate start, LocalDate end){
        ArrayList<Employee> matches = new ArrayList<>();
        for(Employee employee : employees){
            if(employee.dateOfJoin().isAfter(start) && employee.dateOfJoin().isBefore(end)){
                matches.add(employee);
            }
        }
        LOGGER.info("Employees hired within date range [" + start + ", " + end + "]: " + matches);
        return matches;
    }

    public ArrayList<Employee> getEmployeesWithinAgeRange(int start, int end){
        ArrayList<Employee> matches = new ArrayList<>();
        // Create dummy employee with required age
        Employee employee = new Employee(1, "Mr", "Foo", 'B',
                "Bar", 'm', "email@email.com", LocalDate.of(1999, 10, 30),
                LocalDate.of(2024, 4, 8), 0, start);
        // Use binary search to find a employee with the same age, otherwise negate and + 1 index to get the first age
        // that is at least start
        int index = Collections.binarySearch(employeesByAge, employee, Comparator.comparingInt(Employee::age));
        if (index < 0){
            index = -(index + 1);
        }
        // If multiple employee have same age, find the first instance of it
        while(index > 0 &&  employeesByAge.get(index-1).age() >= start){
            index--;
        }

        while(index < employeesByAge.size() && employeesByAge.get(index).age() <= end){
            matches.add(employeesByAge.get(index));
            index++;
        }
        LOGGER.info("Employees within age range [" + start + ", " + end + "]: " + matches);
        return matches;
    }

}
