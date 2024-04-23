package com.sparta.ps.kimchi;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class EmployeeDAO {

    private ArrayList<Employee> employees;

    public EmployeeDAO(ArrayList<Employee> employees){
        this.employees = employees;
    }

    public Employee getEmployeeByID(int id){
        for(Employee employee : employees){
            if(employee.empID() == id){
                return employee;
            }
        }
        return null;
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
