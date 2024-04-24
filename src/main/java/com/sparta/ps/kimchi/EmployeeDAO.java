package com.sparta.ps.kimchi;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Hashtable;

public class EmployeeDAO {

    private ArrayList<Employee> employees;
    private Hashtable<Integer, Employee> employeeID = new Hashtable<>();

    public EmployeeDAO(ArrayList<Employee> employees){
        this.employees = employees;

        for(Employee employee : employees){
            employeeID.put(employee.empID(), employee);
        }
    }

    public void addEmployee(Employee employee){
        employees.add(employee);
        employeeID.put(employee.empID(), employee);
    }

    public void deleteEmployee(Employee employee){
        employees.remove(employee);
        employeeID.remove(employee.empID());
    }

    public Employee getEmployeeByID(int id){
        if(employeeID.contains(id)){
            return employeeID.get(id);
        }else{
            return null;
        }
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
        for(Employee employee : employees){
            if(employee.dateOfJoin().isAfter(start) && employee.dateOfJoin().isBefore(end)){
                matches.add(employee);
            }
        }
        return matches;
    }

    public ArrayList<Employee> getEmployeesWithinAgeRange(int start, int end){
        ArrayList<Employee> matches = new ArrayList<>();
        for(Employee employee : employees){
            int age = Period.between(employee.dateOfBirth(), LocalDate.now()).getYears();
            if(age >= start && age <= end){
                matches.add(employee);
            }
        }
        return matches;
    }

}
