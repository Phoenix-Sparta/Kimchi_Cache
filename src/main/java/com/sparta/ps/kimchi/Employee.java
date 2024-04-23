package com.sparta.ps.kimchi;

import java.time.LocalDate;

public record Employee(int empID, char prefix, String firstName, char middleInitial, String lastName,
                       char gender, String email, LocalDate dateOfBirth, LocalDate dateOfJoin, int salary) { }
