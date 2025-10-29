package br.com.sccon.cleanach.application.gateways;

import br.com.sccon.cleanach.domain.Person;
import br.com.sccon.cleanach.domain.dto.AgeOutput;
import br.com.sccon.cleanach.domain.dto.SalaryOutput;

import java.math.BigDecimal;
import java.util.Map;

public interface PersonRepository {

    public Person personPersistence(Person body);

    public Map<Long, Person> downloadPersons();

    public Person downloadOnePerson(Long id);

    public void removePerson(Long id);

    public Person replacePerson(Long id, Person body);

    public Person updatePerson(Long id, Map<String, Object> fields);

    public long agePerson(Long id, AgeOutput out);

    public BigDecimal salaryPerson(Long id, SalaryOutput out);

}
