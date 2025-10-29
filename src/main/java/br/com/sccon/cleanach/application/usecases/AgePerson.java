package br.com.sccon.cleanach.application.usecases;

import br.com.sccon.cleanach.application.gateways.PersonRepository;
import br.com.sccon.cleanach.domain.Person;
import br.com.sccon.cleanach.domain.dto.AgeOutput;

import java.util.Map;

public class AgePerson {

    private final PersonRepository personRepository;

    public AgePerson(PersonRepository personRepository) {

        this.personRepository = personRepository;
    }

    public long agePerson(Long id, AgeOutput out) {

        return personRepository.agePerson(id, out);
    }

}
