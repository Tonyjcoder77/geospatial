package br.com.sccon.cleanach.application.usecases;

import br.com.sccon.cleanach.application.gateways.PersonRepository;
import br.com.sccon.cleanach.domain.Person;

import java.util.Map;

public class ListOnePerson {

    private final PersonRepository personRepository;

    public ListOnePerson(PersonRepository personRepository) {

        this.personRepository = personRepository;
    }

    public Person listOfPerson(Long id) {
        System.out.println("listofPerson="+id);
        return personRepository.downloadOnePerson(id);
    }

}
