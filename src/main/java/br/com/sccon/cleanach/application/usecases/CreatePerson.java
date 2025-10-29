package br.com.sccon.cleanach.application.usecases;

import br.com.sccon.cleanach.application.gateways.PersonRepository;
import br.com.sccon.cleanach.domain.Person;

public class CreatePerson {

    private final PersonRepository personRepository;

    public CreatePerson(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person creationOfPerson(Person body) {
          return personRepository.personPersistence(body);
    }

}
