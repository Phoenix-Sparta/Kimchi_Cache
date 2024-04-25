package com.sparta.ps.kimchi;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Logger;

import static com.sparta.ps.kimchi.EmployeeDAO.*;
import static com.sparta.ps.kimchi.EmployeeParser.parseEmployeeRecord;

public class EmployeeAPIs {

    static final Logger LOGGER = Logger.getLogger(EmployeeDAO.class.getName());

    public EmployeeAPIs(ArrayList<Employee> employees) throws IOException {
        employeeDAOSetUp(employees);
        EmployeeLogger.configureLogger(LOGGER);
    }

    public void addEmployee(String employee){
        createEmployee(parseEmployeeRecord(employee, DateTimeFormatter.ofPattern("M/d/yyyy")));
    }

    public void addEmployee(Employee employee){
        createEmployee(employee);
    }

    public void addEmployee(ArrayList<Employee> employees){
        for(Employee employee : employees){
            createEmployee(employee);
        }
    }

    public void removeEmployee(String employee){
        deleteEmployee(parseEmployeeRecord(employee, DateTimeFormatter.ofPattern("M/d/yyyy")));
    }

    public void removeEmployee(Employee employee){
        deleteEmployee(employee);
    }

    public void removeEmployee(ArrayList<Employee> employees){
        for(Employee employee : employees){
            deleteEmployee(employee);
        }
    }


    public Employee getEmployeeByID(int id){
        return getEmployeeID().getOrDefault(id, null);
    }

    public ArrayList<Employee> getEmployeeByLastNamePartial(String lastName){
        ArrayList<Employee> matches = new ArrayList<>();
        for(Employee employee : getEmployees()){
            if(employee.lastName().contains(lastName)){
                matches.add(employee);
            }
        }
        LOGGER.info("Employees found by last name partial '" + lastName + "': " + matches);
        return matches;
    }

    public ArrayList<Employee> getEmployeesHiredWithinDateRange(LocalDate start, LocalDate end){
        ArrayList<Employee> matches = new ArrayList<>();
        // Create dummy employee with start date
        Employee dummyEmployee = new Employee(1, "Mr", "Foo", 'B',
                "Bar", 'm', "email@email.com", LocalDate.of(1999, 10, 30),
                start, 0, 24);

        int index = getIndex(getEmployeesByJoinDate(),dummyEmployee, Comparator.comparing(Employee::dateOfJoin));

        // If multiple employee have same join date, find first instance of it
        while(index > 0 &&  getEmployeesByJoinDate().get(index - 1).dateOfJoin().isEqual(start)){
            index--;
        }

        while(index < getNumOfEmployees() && (getEmployeesByJoinDate().get(index).dateOfJoin().isBefore(end)
                || getEmployeesByJoinDate().get(index).dateOfJoin().isEqual(end))){
            matches.add(getEmployeesByJoinDate().get(index));
            index++;
        }

        LOGGER.info("Employees hired within date range [" + start + ", " + end + "]: " + matches);
        return matches;
    }

    public ArrayList<Employee> getEmployeesWithinAgeRange(int start, int end){
        ArrayList<Employee> matches = new ArrayList<>();
        // Create dummy employee with start age
        Employee dummyEmployee = new Employee(1, "Mr", "Foo", 'B',
                "Bar", 'm', "email@email.com", LocalDate.of(1999, 10, 30),
                LocalDate.of(2024, 4, 8), 0, start);

        // Use binary search to find a employee with the same age
        int index = getIndex(getEmployeesByAge(),dummyEmployee, Comparator.comparingInt(Employee::age));

        // If multiple employee have same age, find the first instance of it
        while(index > 0 && getEmployeesByAge().get(index-1).age() >= start){
            index--;
        }

        while(index < getNumOfEmployees() && getEmployeesByAge().get(index).age() <= end){
            matches.add(getEmployeesByAge().get(index));
            index++;
        }
        LOGGER.info("Employees within age range [" + start + ", " + end + "]: " + matches);
        return matches;
    }

    public ArrayList<Employee> getEmployeesWithinSalaryRange(int start, int end){
        ArrayList<Employee> matches = new ArrayList<>();
        // Create dummy employee with start salary
        Employee dummyEmployee = new Employee(1, "Mr", "Foo", 'B',
                "Bar", 'm', "email@email.com", LocalDate.of(1999, 10, 30),
                LocalDate.of(2024, 4, 8), start, 100);

        int index = getIndex(getEmployeesBySalary(), dummyEmployee, Comparator.comparingInt(Employee::salary));

        while(index > 0 && getEmployeesByAge().get(index - 1).salary() >= start){
            index--;
        }

        while(index < getNumOfEmployees() && getEmployeesBySalary().get(index).salary() <= end){
            matches.add(getEmployeesBySalary().get(index));
            index++;
        }
        return matches;
    }

    public ArrayList<Employee> getEmployeeByGender(char gender){
        ArrayList<Employee> matches = new ArrayList<>();
        for(Employee employee : getEmployees()){
            if(employee.gender() == gender){
                matches.add(employee);
            }
        }
        return matches;
    }

    private int getIndex(ArrayList<Employee> employees, Employee dummyEmployee, Comparator<Employee> comparator){
        int index = Collections.binarySearch(employees, dummyEmployee, comparator);
        return (index < 0) ? index : -(index + 1);
    }
}
