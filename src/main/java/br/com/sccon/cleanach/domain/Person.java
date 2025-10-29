package br.com.sccon.cleanach.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class Person {

    private Long id;

    private String name;

    private LocalDate dateOfBirth;

    private LocalDate dateOfAdmission;

    public Person(Long id, String name, LocalDate dateOfBirth, LocalDate dateOfAdmission) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfAdmission = dateOfAdmission;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfAdmission() {
        return dateOfAdmission;
    }

    public void setDateOfAdmission(LocalDate dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }
}
