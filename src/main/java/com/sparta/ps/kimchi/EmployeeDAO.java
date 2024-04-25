package com.sparta.ps.kimchi;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Logger;

import static com.sparta.ps.kimchi.EmployeeDTO.*;

public class EmployeeDAO implements DAOEnabler {

    static final Logger LOGGER = Logger.getLogger(EmployeeDAO.class.getName());

    public EmployeeDAO(ArrayList<Employee> employees) throws IOException {
        employeeDAOSetUp(employees);
        EmployeeLogger.configureLogger(LOGGER);
    }

    /**
     * Returns the Employee object with the ID given.
     * If no employee exists with ID given, return null
     * @param id  The Employee ID to search for
     * @return The Employee object which matches the ID given or null
     */
    public Employee getEmployeeByID(int id){
        LOGGER.info("Getting employee with ID " + id);
        return getEmployeeID().getOrDefault(id, null);
    }

    /**
     * Returns an ArrayList of all employee  whose last name contains the last name given
     * Only works if the lastName given is part of the Employee's last name, doesn't account
     * for spelling mistakes.
     * @param lastName The last name to partial match with
     * @return ArrayList of Employee object whose last name contains lastName
     */
    public ArrayList<Employee> getEmployeeByLastNamePartial(String lastName){
        LOGGER.info("Finding employee with last name that contains " + lastName);
        ArrayList<Employee> matches = new ArrayList<>();
        for(Employee employee : getEmployees()){
            if(employee.lastName().contains(lastName)){
                LOGGER.fine("Match found");
                matches.add(employee);
            }
        }
        LOGGER.info("All employee with last name which contains " + lastName + "\n" + matches);
        return matches;
    }

    /**
     * Returns an ArrayList of all employee who was hired within the date range given.
     * Date range given must be valid, startingDate - endingDate, if given in wrong order, it will return nothing
     * Date must be in LocalDate format - LocalDate.of(yyyy,mm,dd)
     * e.g. 23/4/2023 Should be entered as LocalDate.of(2023,4,23)
     * @param startingDate The starting date
     * @param endingDate The ending date
     * @return ArrayList of all employee who was hired between those dates
     */
    public ArrayList<Employee> getEmployeesHiredWithinDateRange(LocalDate startingDate, LocalDate endingDate){
        LOGGER.info("Getting employees who was hired within " + startingDate + " and " + endingDate);
        ArrayList<Employee> matches = new ArrayList<>();
        // Create dummy employee with startingDate date
        Employee dummyEmployee = new Employee(1, "Mr", "Foo", 'B',
                "Bar", 'm', "email@email.com", LocalDate.of(1999, 10, 30),
                startingDate, 0, 24);

        int index = getIndex(getEmployeesByJoinDate(),dummyEmployee, Comparator.comparing(Employee::dateOfJoin));

        // If multiple employee have same join date, find first instance of it
        while(index > 0 &&  getEmployeesByJoinDate().get(index-1).dateOfJoin().isEqual(startingDate)){
            index--;
        }

        while(index < getNumOfEmployees() && (getEmployeesByJoinDate().get(index).dateOfJoin().isBefore(endingDate)
                || getEmployeesByJoinDate().get(index).dateOfJoin().isEqual(endingDate))){
            LOGGER.fine("Match found");
            matches.add(getEmployeesByJoinDate().get(index));
            index++;
        }

        LOGGER.info("Employees hired within date range [" + startingDate + ", " + endingDate + "]: " + matches);
        return matches;
    }

    /**
     * Returns all employee who was hired on the date given.
     * Date must be in LocalDate format - LocalDate.of(yyyy,mm,dd)
     * e.g. 23/4/2023 Should be entered as LocalDate.of(2023,4,23)
     * @param date The date
     * @return  ArrayList of employee who was hired on date given
     */
    public ArrayList<Employee> getEmployeesHiredDate(LocalDate date){
        LOGGER.info("Getting employees who was hired on " + date);
        ArrayList<Employee> matches = new ArrayList<>();
        // Create dummy employee with date
        Employee dummyEmployee = new Employee(1, "Mr", "Foo", 'B',
                "Bar", 'm', "email@email.com", LocalDate.of(1999, 10, 30),
                date, 0, 24);

        int index = Collections.binarySearch(getEmployeesByJoinDate(), dummyEmployee, Comparator.comparing(Employee::dateOfJoin));
        // Index returns less than 0 if no match was found
        if(index < 0){
            LOGGER.info("No employees hired at " + date);
            return null;
        }

        // If multiple employee have same join date, find first instance of it
        while(index > 0 &&  getEmployeesByJoinDate().get(index-1).dateOfJoin().isEqual(date)){
            index--;
        }

        while(index < getNumOfEmployees() && (getEmployeesByJoinDate().get(index).dateOfJoin().isEqual(date))){
            LOGGER.fine("Match found");
            matches.add(getEmployeesByJoinDate().get(index));
            index++;
        }

        LOGGER.info("Employees hired at date [" + date + "]: " + matches);
        return matches;
    }

    /**
     * Returns all employee whose age is between minAge and maxAge
     * Input given must be in right order, otherwise it will return nothing
     * @param minAge The minimum age
     * @param maxAge The maximum age
     * @return ArrayList of employee whose age is between minAge and maxAge
     */
    public ArrayList<Employee> getEmployeesWithinAgeRange(int minAge, int maxAge){
        LOGGER.info("Getting employees with age range " + minAge + " and " + maxAge);
        ArrayList<Employee> matches = new ArrayList<>();
        // Create dummy employee with minAge age
        Employee dummyEmployee = new Employee(1, "Mr", "Foo", 'B',
                "Bar", 'm', "email@email.com", LocalDate.of(1999, 10, 30),
                LocalDate.of(2024, 4, 8), 0, minAge);

        // Use binary search to find a employee with the same age
        int index = getIndex(getEmployeesByAge(),dummyEmployee, Comparator.comparingInt(Employee::age));

        // If multiple employee have same age, find the first instance of it
        while(index > 0 && getEmployeesByAge().get(index-1).age() >= minAge){
            index--;
        }

        while(index < getNumOfEmployees() && getEmployeesByAge().get(index).age() <= maxAge){
            LOGGER.fine("Match found");
            matches.add(getEmployeesByAge().get(index));
            index++;
        }
        LOGGER.info("Employees within age range [" + minAge + ", " + maxAge + "]: " + matches);
        return matches;
    }

    /**
     * Returns all employee whose age matches the given age
     * @param age The age of employee to search for
     * @return All employee whose age is age
     */
    public ArrayList<Employee> getEmployeesWithAge(int age){
        LOGGER.info("Getting employees with age " + age);
        ArrayList<Employee> matches = new ArrayList<>();
        // Create dummy employee with age
        Employee dummyEmployee = new Employee(1, "Mr", "Foo", 'B',
                "Bar", 'm', "email@email.com", LocalDate.of(1999, 10, 30),
                LocalDate.of(2024, 4, 8), 0, age);

        // Use binary search to find a employee with the same age
        int index = Collections.binarySearch(getEmployeesByAge(), dummyEmployee, Comparator.comparingInt(Employee::age));

        if(index < 0){
            LOGGER.info("No employees with age " + age);
            return null;
        }

        // If multiple employee have same age, find the first instance of it
        while(index > 0 && getEmployeesByAge().get(index-1).age() >= age){
            index--;
        }

        while(index < getNumOfEmployees() && getEmployeesByAge().get(index).age() == age){
            LOGGER.fine("Match found");
            matches.add(getEmployeesByAge().get(index));
            index++;
        }
        LOGGER.info("Employees with age [" + age + "]: " + matches);
        return matches;
    }

    /**
     * Returns all employee whose salary is within the range given.
     * Input must be given in the right order otherwise it will return empty array
     * @param minSalary The minimum salary
     * @param maxSalary The maximum salary
     * @return ArrayList of all employee whose salary is within minSalary and maxSalary
     */
    public ArrayList<Employee> getEmployeesWithinSalaryRange(int minSalary, int maxSalary){
        LOGGER.info("Getting employee with salary range " + minSalary + " and " + maxSalary);
        ArrayList<Employee> matches = new ArrayList<>();
        // Create dummy employee with minSalary salary
        Employee dummyEmployee = new Employee(1, "Mr", "Foo", 'B',
                "Bar", 'm', "email@email.com", LocalDate.of(1999, 10, 30),
                LocalDate.of(2024, 4, 8), minSalary, 100);

        int index = getIndex(getEmployeesBySalary(), dummyEmployee, Comparator.comparingInt(Employee::salary));

        while(index > 0 && getEmployeesByAge().get(index - 1).salary() >= minSalary){
            index--;
        }

        while(index < getNumOfEmployees() && getEmployeesBySalary().get(index).salary() <= maxSalary){
            LOGGER.fine("Match found");
            matches.add(getEmployeesBySalary().get(index));
            index++;
        }
        LOGGER.info("Employees with age [" + minSalary + " " + maxSalary + " ]: " + matches);
        return matches;
    }

    /**
     * Returns all employee whose gender is gender
     * Only accepts char, ensure single quotation marks are used ' '
     * @param gender 'M' for male 'F' for female
     * @return ArrayList of all employee whose gender is gender
     */
    public ArrayList<Employee> getEmployeeByGender(char gender){
        LOGGER.info("Getting employees with the gender " + gender);
        ArrayList<Employee> matches = new ArrayList<>();
        for(Employee employee : getEmployees()){
            if(employee.gender() == gender){
                LOGGER.fine("Match found");
                matches.add(employee);
            }
        }
        LOGGER.info("All employee with gender " + gender + "\n" + matches);
        return matches;
    }

    private int getIndex(ArrayList<Employee> employees, Employee dummyEmployee, Comparator<Employee> comparator){
        int index = Collections.binarySearch(employees, dummyEmployee, comparator);
        return (index > 0) ? index : -(index + 1);
    }
}
