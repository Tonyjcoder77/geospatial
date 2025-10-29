package br.com.sccon.cleanach.infra.gateways;

import br.com.sccon.cleanach.application.gateways.PersonRepository;
import br.com.sccon.cleanach.domain.Person;
import br.com.sccon.cleanach.domain.dto.AgeOutput;
import br.com.sccon.cleanach.domain.dto.SalaryOutput;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

    // mapa em memória
    private final Map<Long, Person> persons = new ConcurrentHashMap<>();
    private static final BigDecimal MIN_WAGE = new BigDecimal("1302.00"); // (fev/2023)
    private static final BigDecimal START_SALARY = new BigDecimal("1556.00");

    public PersonRepositoryInMemory() {
        // 3 pessoas iniciais
        persons.put(1L, new Person(1L, "José da Silva",
                LocalDate.of(2000, 4, 6), LocalDate.of(2020, 5, 10)));
        persons.put(2L, new Person(2L, "Ana Paula Ayrosa",
                LocalDate.of(1998, 9, 12), LocalDate.of(2019, 3, 4)));
        persons.put(3L, new Person(3L,"Bruno Costa Quente",
                LocalDate.of(1995, 1, 20), LocalDate.of(2022, 1, 15)));
    }

    // 1) lista ordenada por nome
    @Override
    public Map<Long, Person> downloadPersons() {

        return this.persons;
    }

    // 2) busca por id
    @Override
    public Person downloadOnePerson(Long id) {
        var p = this.persons.get(id);
        if (p == null) throw new ResponseStatusException(NOT_FOUND, "Pessoa não encontrada");
        return p;
    }

    // 3) cria
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

    // 4) delete
    @Override
    public void removePerson(Long id) {
        if (this.persons.remove(id) == null)
            throw new ResponseStatusException(NOT_FOUND, "Pessoa não encontrada");
    }

    // 5) update total (PUT)
    @Override
    public Person replacePerson(Long id, Person body) {
        if (!this.persons.containsKey(id)) throw new ResponseStatusException(NOT_FOUND, "Pessoa não encontrada");
        body.setId(id);
        this.persons.put(id, body);
        return body;
    }

    // 6) patch (atributos parciais)
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

    // 7) idade em days|months|years
    @Override
    public long agePerson(Long id, AgeOutput out) {
        var p = downloadOnePerson(id);
        return switch (out) {
            case days -> ChronoUnit.DAYS.between(p.getDateOfBirth(), LocalDate.now());
            case months -> ChronoUnit.MONTHS.between(p.getDateOfBirth(), LocalDate.now());
            case years -> ChronoUnit.YEARS.between(p.getDateOfBirth(), LocalDate.now());
        };
    }

    // 8) salário atual: full (R$) ou min (qtd de salários mínimos)
    @Override
    public BigDecimal salaryPerson(Long id, SalaryOutput out) {
        var p = downloadOnePerson(id);
        int yearsInCompany = (int) ChronoUnit.YEARS.between(p.getDateOfAdmission(), LocalDate.now());

        // Regra do enunciado:
        // inicia em 1556,00 e a CADA ANO: salario = salario * 1.18 + 500
        BigDecimal s = START_SALARY;
        for (int i = 0; i < yearsInCompany; i++) {
            s = s.multiply(new BigDecimal("1.18")).add(new BigDecimal("500"));
        }

        if (out == SalaryOutput.full) {
            // duas casas decimais, arredondando para cima
            return s.setScale(2, RoundingMode.CEILING);
        } else {
            // quantidade de salários mínimos (duas casas, arredondando para cima)
            return s.divide(MIN_WAGE, 2, RoundingMode.CEILING);
        }
    }

}
