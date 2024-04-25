package com.sparta.ps.kimchi;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Logger;

import static com.sparta.ps.kimchi.EmployeeDTO.*;
import static com.sparta.ps.kimchi.EmployeeParser.parseEmployeeRecord;

public class EmployeeDAO implements DAOEnabler {

    static final Logger LOGGER = Logger.getLogger(EmployeeDAO.class.getName());

    public EmployeeDAO(ArrayList<Employee> employees) throws IOException {
        employeeDAOSetUp(employees);
        EmployeeLogger.configureLogger(LOGGER);
    }

    public void addEmployee(String employee){
        createEmployee(parseEmployeeRecord(employee, DateTimeFormatter.ofPattern("M/d/yyyy")));
        LOGGER.info("Added Employee " + employee);
    }

    public void addEmployee(Employee employee){
        createEmployee(employee);
        LOGGER.info("Added Employee " + employee);
    }

    public void addEmployee(ArrayList<Employee> employees){
        LOGGER.info("Adding employees");
        for(Employee employee : employees){
            createEmployee(employee);
            LOGGER.fine("Added Employee " + employee);
        }
    }

    public void removeEmployee(String employee){
        deleteEmployee(parseEmployeeRecord(employee, DateTimeFormatter.ofPattern("M/d/yyyy")));
        LOGGER.info("Deleted employee " + employee);
    }

    public void removeEmployee(Employee employee){
        deleteEmployee(employee);
        LOGGER.info("Deleted employee " + employee);
    }

    public void removeEmployee(ArrayList<Employee> employees){
        LOGGER.info("Deleting employees");
        for(Employee employee : employees){
            deleteEmployee(employee);
            LOGGER.fine("Deleted employee " + employee);
        }
    }

    public Employee getEmployeeByID(int id){
        LOGGER.info("Getting employee with ID " + id);
        return getEmployeeID().getOrDefault(id, null);
    }

    public ArrayList<Employee> getEmployeeByLastNamePartial(String lastName){
        LOGGER.info("Finding employee with last name that contains " + lastName);
        ArrayList<Employee> matches = new ArrayList<>();
        for(Employee employee : getEmployees()){
            if(employee.lastName().contains(lastName)){
                LOGGER.fine("Match found");
                matches.add(employee);
            }
        }
        return matches;
    }

    public ArrayList<Employee> getEmployeesHiredWithinDateRange(LocalDate start, LocalDate end){
        LOGGER.info("Getting employees who was hired within " + start + " and " + end);
        ArrayList<Employee> matches = new ArrayList<>();
        // Create dummy employee with start date
        Employee dummyEmployee = new Employee(1, "Mr", "Foo", 'B',
                "Bar", 'm', "email@email.com", LocalDate.of(1999, 10, 30),
                start, 0, 24);

        int index = getIndex(getEmployeesByJoinDate(),dummyEmployee, Comparator.comparing(Employee::dateOfJoin));

        // If multiple employee have same join date, find first instance of it
        while(index > 0 &&  getEmployeesByJoinDate().get(index-1).dateOfJoin().isEqual(start)){
            index--;
        }

        while(index < getNumOfEmployees() && (getEmployeesByJoinDate().get(index).dateOfJoin().isBefore(end)
                || getEmployeesByJoinDate().get(index).dateOfJoin().isEqual(end))){
            LOGGER.fine("Match found");
            matches.add(getEmployeesByJoinDate().get(index));
            index++;
        }

        LOGGER.info("Employees hired within date range [" + start + ", " + end + "]: " + matches);
        return matches;
    }

    public ArrayList<Employee> getEmployeesHiredDate(LocalDate start){
        LOGGER.info("Getting employees who was hired on " + start);
        ArrayList<Employee> matches = new ArrayList<>();
        // Create dummy employee with start date
        Employee dummyEmployee = new Employee(1, "Mr", "Foo", 'B',
                "Bar", 'm', "email@email.com", LocalDate.of(1999, 10, 30),
                start, 0, 24);

        int index = Collections.binarySearch(getEmployeesByJoinDate(), dummyEmployee, Comparator.comparing(Employee::dateOfJoin));
        // Index returns less than 0 if no match was found
        if(index < 0){
            LOGGER.info("No employees hired at " + start);
            return null;
        }

        // If multiple employee have same join date, find first instance of it
        while(index > 0 &&  getEmployeesByJoinDate().get(index-1).dateOfJoin().isEqual(start)){
            index--;
        }

        while(index < getNumOfEmployees() && (getEmployeesByJoinDate().get(index).dateOfJoin().isEqual(start))){
            LOGGER.fine("Match found");
            matches.add(getEmployeesByJoinDate().get(index));
            index++;
        }

        LOGGER.info("Employees hired at date [" + start + "]: " + matches);
        return matches;
    }


    public ArrayList<Employee> getEmployeesWithinAgeRange(int start, int end){
        LOGGER.info("Getting employees with age range " + start + " and " + end);
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
            LOGGER.fine("Match found");
            matches.add(getEmployeesByAge().get(index));
            index++;
        }
        LOGGER.info("Employees within age range [" + start + ", " + end + "]: " + matches);
        return matches;
    }

    public ArrayList<Employee> getEmployeesWithAge(int start){
        LOGGER.info("Getting employees with age " + start);
        ArrayList<Employee> matches = new ArrayList<>();
        // Create dummy employee with start age
        Employee dummyEmployee = new Employee(1, "Mr", "Foo", 'B',
                "Bar", 'm', "email@email.com", LocalDate.of(1999, 10, 30),
                LocalDate.of(2024, 4, 8), 0, start);

        // Use binary search to find a employee with the same age
        int index = Collections.binarySearch(getEmployeesByAge(), dummyEmployee, Comparator.comparingInt(Employee::age));

        if(index < 0){
            LOGGER.info("No employees with age " + start);
            return null;
        }

        // If multiple employee have same age, find the first instance of it
        while(index > 0 && getEmployeesByAge().get(index-1).age() >= start){
            index--;
        }

        while(index < getNumOfEmployees() && getEmployeesByAge().get(index).age() == start){
            LOGGER.fine("Match found");
            matches.add(getEmployeesByAge().get(index));
            index++;
        }
        LOGGER.info("Employees with age [" + start + "]: " + matches);
        return matches;
    }

    public ArrayList<Employee> getEmployeesWithinSalaryRange(int start, int end){
        LOGGER.info("Getting employee with salary range " + start + " and " + end);
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
            LOGGER.fine("Match found");
            matches.add(getEmployeesBySalary().get(index));
            index++;
        }
        LOGGER.info("Employees with age [" + start + " " + end + " ]: " + matches);
        return matches;
    }

    public ArrayList<Employee> getEmployeeByGender(char gender){
        LOGGER.info("Getting employees by the gender " + gender);
        ArrayList<Employee> matches = new ArrayList<>();
        for(Employee employee : getEmployees()){
            if(employee.gender() == gender){
                LOGGER.fine("Match found");
                matches.add(employee);
            }
        }
        return matches;
    }

    private int getIndex(ArrayList<Employee> employees, Employee dummyEmployee, Comparator<Employee> comparator){
        int index = Collections.binarySearch(employees, dummyEmployee, comparator);
        return (index > 0) ? index : -(index + 1);
    }
}
