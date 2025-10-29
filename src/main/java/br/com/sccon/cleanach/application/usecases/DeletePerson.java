package br.com.sccon.cleanach.application.usecases;

import br.com.sccon.cleanach.application.gateways.PersonRepository;
import br.com.sccon.cleanach.domain.Person;

public class DeletePerson {

    private final PersonRepository personRepository;

    public DeletePerson(PersonRepository personRepository) {

        this.personRepository = personRepository;
    }

    public void removePerson(Long id) {

        personRepository.removePerson(id);
    }

}
