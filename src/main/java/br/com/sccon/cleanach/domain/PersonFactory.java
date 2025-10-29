package br.com.sccon.cleanach.domain;

import java.time.LocalDate;

public class PersonFactory {
    private Person person;

    public Person PersonWithData(Long id, String name, LocalDate dob, LocalDate adm) {
        this.person = new Person(id, name, dob, adm);
        return this.person;
    }
}
