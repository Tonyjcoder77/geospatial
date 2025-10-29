package br.com.sccon.cleanach.application.usecases;

import br.com.sccon.cleanach.application.gateways.PersonRepository;
import br.com.sccon.cleanach.domain.Person;
import br.com.sccon.cleanach.domain.dto.AgeOutput;

import java.util.Map;

public class UpdatePerson {

    private final PersonRepository personRepository;

    public UpdatePerson(PersonRepository personRepository) {

        this.personRepository = personRepository;
    }

    public Person updatePerson(Long id, Map<String, Object> fields) {

        return personRepository.updatePerson(id, fields);
    }

}
