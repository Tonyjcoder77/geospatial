package br.com.sccon.cleanach.application.usecases;

import br.com.sccon.cleanach.application.gateways.PersonRepository;
import br.com.sccon.cleanach.domain.Person;

public class ReplacePerson {

    private final PersonRepository personRepository;

    public ReplacePerson(PersonRepository personRepository) {

        this.personRepository = personRepository;
    }

    public Person replacePerson(Long id, Person body) {

        return personRepository.replacePerson(id, body);
    }

}
