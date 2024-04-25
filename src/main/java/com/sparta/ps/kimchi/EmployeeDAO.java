package com.sparta.ps.kimchi;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class EmployeeDAO {

    private ArrayList<Employee> employees;
    private ArrayList<Employee> employeesByAge;
    private ArrayList<Employee> employeesByJoinDate;
    private Hashtable<Integer, Employee> employeeID = new Hashtable<>();

    private static int numOfEmployees;

    public EmployeeDAO(ArrayList<Employee> employees){
        this.employees = employees;
        numOfEmployees = employees.size();

        this.employeesByAge = employees;
        employeesByAge.sort(Comparator.comparingInt(Employee::age));

        this.employeesByJoinDate = employees;
        employeesByJoinDate.sort(Comparator.comparing(Employee::dateOfJoin));

        for(Employee employee : employees){
            employeeID.put(employee.empID(), employee);
        }
    }

    public void addEmployee(Employee employee){
        employees.add(employee);
        employeeID.put(employee.empID(), employee);
    }

    public String readEmployee(int id){
        return employeeID.get(id).toString();
    }

    public Hashtable<Integer, Employee> getEmployeeIDHashtable(){
        return employeeID;
    }

    public void deleteEmployee(Employee employee){
        employees.remove(employee);
        employeeID.remove(employee.empID());
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
        return matches;
    }

    public ArrayList<Employee> getEmployeesHiredWithinDateRange(LocalDate start, LocalDate end){
        ArrayList<Employee> matches = new ArrayList<>();
        Employee dummyEmployee = new Employee(1, "Mr", "Foo", 'B',
                "Bar", 'm', "email@email.com", LocalDate.of(1999, 10, 30),
                start, 0, 24);

        int index = getIndex(employeesByJoinDate,dummyEmployee, Comparator.comparing(Employee::dateOfJoin));

        // If multiple employee have same join date, find first instance of it
        while(index > 0 &&  employeesByJoinDate.get(index - 1).dateOfJoin().isEqual(start)){
            index--;
        }

        while(index < numOfEmployees && (employeesByJoinDate.get(index).dateOfJoin().isBefore(end)
                                        || employeesByJoinDate.get(index).dateOfJoin().isEqual(end))){
            matches.add(employeesByJoinDate.get(index));
            index++;
        }

        return matches;
    }

    public ArrayList<Employee> getEmployeesWithinAgeRange(int start, int end){
        ArrayList<Employee> matches = new ArrayList<>();
        // Create dummy employee with required age
        Employee dummyEmployee = new Employee(1, "Mr", "Foo", 'B',
                "Bar", 'm', "email@email.com", LocalDate.of(1999, 10, 30),
                LocalDate.of(2024, 4, 8), 0, start);
        // Use binary search to find a employee with the same age, otherwise negate and + 1 index to get the first age
        // that is at least start
//        int index = Collections.binarySearch(employeesByAge, employee, Comparator.comparingInt(Employee::age));
//        if (index < 0){
//            index = -(index + 1);
//        }
        int index = getIndex(employeesByAge,dummyEmployee, Comparator.comparingInt(Employee::age));

        // If multiple employee have same age, find the first instance of it
        while(index > 0 &&  employeesByAge.get(index-1).age() >= start){
            index--;
        }

        while(index < numOfEmployees && employeesByAge.get(index).age() <= end){
            matches.add(employeesByAge.get(index));
            index++;
        }
        return matches;
    }

    private int getIndex(ArrayList<Employee> employees, Employee dummyEmployee, Comparator<Employee> comparator){
        int index = Collections.binarySearch(employees, dummyEmployee, comparator);
        return (index < 0) ? index : -(index + 1);
    }

}
