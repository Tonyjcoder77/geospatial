package br.com.sccon.cleanach.application.usecases;

import br.com.sccon.cleanach.application.gateways.PersonRepository;
import br.com.sccon.cleanach.domain.Person;

import java.util.Map;

public class ListAllPersons {

    private final PersonRepository personRepository;

    public ListAllPersons(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Map<Long, Person> listOfPersons() {
        return personRepository.downloadPersons();
    }

}
