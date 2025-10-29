package br.com.sccon.cleanach.application.usecases;

import br.com.sccon.cleanach.application.gateways.PersonRepository;
import br.com.sccon.cleanach.domain.dto.AgeOutput;
import br.com.sccon.cleanach.domain.dto.SalaryOutput;

import java.math.BigDecimal;

public class SalaryPerson {

    private final PersonRepository personRepository;

    public SalaryPerson(PersonRepository personRepository) {

        this.personRepository = personRepository;
    }

    public BigDecimal salaryPerson(Long id, SalaryOutput out) {

        return personRepository.salaryPerson(id, out);
    }

}
