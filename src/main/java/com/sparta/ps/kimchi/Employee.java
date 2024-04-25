package com.sparta.ps.kimchi;

import java.time.LocalDate;
import java.util.Objects;

public record Employee(int empID, String prefix, String firstName, char middleInitial, String lastName,
                       char gender, String email, LocalDate dateOfBirth, LocalDate dateOfJoin, int salary, int age) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return empID == employee.empID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(empID);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empID=" + empID + '\'' +
                ", prefix=" + prefix +
                ", firstName='" + firstName + '\'' +
                ", middleInitial=" + middleInitial +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", dateOfJoin=" + dateOfJoin +
                ", salary=" + salary +
                '}';
    }
}
