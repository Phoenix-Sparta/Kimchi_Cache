package com.sparta.ps.kimchi;

import java.time.LocalDate;

public record Employee(int empID, String prefix, String firstName, char middleInitial, String lastName,
                       char gender, String email, LocalDate dateOfBirth, LocalDate dateOfJoin, int salary, int age) {

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
