package br.com.sccon.cleanach.infra.gateways;

import br.com.sccon.cleanach.application.gateways.PersonRepository;
import br.com.sccon.cleanach.domain.Person;
import br.com.sccon.cleanach.domain.enums.AgeOutput;
import br.com.sccon.cleanach.domain.enums.SalaryOutput;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class PersonRepositoryInMemory implements PersonRepository {

    private final Map<Long, Person> persons = new ConcurrentHashMap<>();
    private static final BigDecimal MIN_WAGE = new BigDecimal("1302.00"); // (fev/2023)
    private static final BigDecimal START_SALARY = new BigDecimal("1558.00");

    public PersonRepositoryInMemory() {
        // 3 pessoas iniciais
        persons.put(1L, new Person(1L, "José da Silva",
                LocalDate.of(2000, 4, 6), LocalDate.of(2020, 5, 10)));
        persons.put(2L, new Person(2L, "Ronald Reagan",
                LocalDate.of(2005, 2, 01), LocalDate.of(2015, 7, 12)));
        persons.put(3L, new Person(3L,"Santos Dummont",
                LocalDate.of(2014, 10, 14), LocalDate.of(2025, 4, 21)));
    }

    @Override
    public Map<Long, Person> downloadPersons() {

        return this.persons;
    }

    @Override
    public Person downloadOnePerson(Long id) {
        var p = this.persons.get(id);
        if (p == null) throw new ResponseStatusException(NOT_FOUND, "Pessoa não encontrada");
        return p;
    }

    @Override
    public Person personPersistence(Person body) {
        if (body.getId() == null) {
            long next = this.persons.keySet().stream().mapToLong(Long::longValue).max().orElse(0L) + 1;
            body.setId(next);
        } else if (this.persons.containsKey(body.getId())) {
            throw new ResponseStatusException(CONFLICT, "Já existe pessoa com id=" + body.getId());
        }
        this.persons.put(body.getId(), body);
        return body;
    }

    @Override
    public void removePerson(Long id) {
        if (this.persons.remove(id) == null)
            throw new ResponseStatusException(NOT_FOUND, "Pessoa não encontrada");
    }

    @Override
    public Person replacePerson(Long id, Person body) {
        if (!this.persons.containsKey(id)) throw new ResponseStatusException(NOT_FOUND, "Pessoa não encontrada");
        body.setId(id);
        this.persons.put(id, body);
        return body;
    }

    @Override
    public Person updatePerson(Long id, Map<String, Object> fields) {
        var p = this.persons.get(id);
        if (p == null) throw new ResponseStatusException(NOT_FOUND, "Pessoa não encontrada");
        fields.forEach((k, v) -> {
            switch (k) {
                case "name" -> p.setName(String.valueOf(v));
                case "dateOfBirth" -> p.setDateOfBirth(LocalDate.parse(String.valueOf(v)));
                case "dateOfAdmission" -> p.setDateOfAdmission(LocalDate.parse(String.valueOf(v)));
                case "id" -> throw new ResponseStatusException(BAD_REQUEST, "Não é permitido alterar o id");
                default -> throw new ResponseStatusException(BAD_REQUEST, "Campo inválido em patch: " + k);
            }
        });
        return p;
    }

    @Override
    public long agePerson(Long id, AgeOutput out) {
        var p = downloadOnePerson(id);
        return switch (out) {
            case days -> ChronoUnit.DAYS.between(p.getDateOfBirth(), LocalDate.now());
            case months -> ChronoUnit.MONTHS.between(p.getDateOfBirth(), LocalDate.now());
            case years -> ChronoUnit.YEARS.between(p.getDateOfBirth(), LocalDate.now());
        };
    }

    @Override
    public BigDecimal salaryPerson(Long id, SalaryOutput out) {
        var p = downloadOnePerson(id);
        int yearsInCompany = (int) ChronoUnit.YEARS.between(p.getDateOfAdmission(), LocalDate.now());

        BigDecimal s = START_SALARY;
        for (int i = 0; i < yearsInCompany; i++) {
            s = s.multiply(new BigDecimal("1.18")).add(new BigDecimal("500"));
        }

        if (out == SalaryOutput.full) {
            return s.setScale(2, RoundingMode.CEILING);
        } else {
            return s.divide(MIN_WAGE, 2, RoundingMode.CEILING);
        }
    }

}
