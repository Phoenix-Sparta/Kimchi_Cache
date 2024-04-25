package com.sparta.ps.kimchi;

import java.time.LocalDate;
import java.util.ArrayList;

public interface DAOEnabler {
    Employee getEmployeeByID(int id);

    ArrayList<Employee> getEmployeeByLastNamePartial(String lastName);

    ArrayList<Employee> getEmployeesHiredWithinDateRange(LocalDate start, LocalDate end);

    ArrayList<Employee> getEmployeesHiredDate(LocalDate start);

    ArrayList<Employee> getEmployeesWithinAgeRange(int start, int end);

    ArrayList<Employee>  getEmployeesWithAge(int start);

    ArrayList<Employee> getEmployeesWithinSalaryRange(int start, int end);

    ArrayList<Employee> getEmployeeByGender(char gender);
}