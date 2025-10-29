package br.com.sccon.cleanach.infra.controller;

import br.com.sccon.cleanach.application.usecases.*;

import javax.validation.Valid;

import br.com.sccon.cleanach.domain.Person;
import br.com.sccon.cleanach.domain.dto.AgeOutput;
import br.com.sccon.cleanach.domain.dto.SalaryOutput;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final CreatePerson createPerson;
    private final ListAllPersons listAllPersons;
    private final ListOnePerson listOnePerson;
    private final DeletePerson deletePerson;
    private final ReplacePerson replacePerson;
    private final UpdatePerson updatePerson;
    private final AgePerson agePerson;
    private final SalaryPerson salaryPerson;

    public PersonController(CreatePerson createPerson, ListAllPersons listAllPersons,
                            ListOnePerson listOnePerson, DeletePerson deletePerson,
                            ReplacePerson replacePerson, UpdatePerson updatePerson,
                            AgePerson agePerson, SalaryPerson salaryPerson) {
        this.createPerson = createPerson;
        this.listAllPersons = listAllPersons;
        this.listOnePerson = listOnePerson;
        this.deletePerson = deletePerson;
        this.replacePerson = replacePerson;
        this.updatePerson = updatePerson;
        this.agePerson = agePerson;
        this.salaryPerson = salaryPerson;
    }

    // 1) lista ordenada por nome
    @GetMapping
    public Map<Long, Person> list() {
        System.out.println("list()<---------------");
        return listAllPersons.listOfPersons();
    }

    // 2) busca por id
    @GetMapping("/{id}")
    public Person get(@PathVariable Long id) {
        System.out.println("list(one)<---------------"+id);
        return listOnePerson.listOfPerson(id);
    }

    // 3) cria (POST)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person personCriation(@Valid @RequestBody Person body) {
        System.out.println("personCriation()<---------------");
        return createPerson.creationOfPerson(body);
    }

    // 4) DELETE remove
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {

        deletePerson.removePerson(id);
    }

    // 5) PUT atualiza totalmente
    @PutMapping("/{id}")
    public Person replace(@PathVariable Long id, @Valid @RequestBody Person body) {

        return replacePerson.replacePerson(id, body);
    }

    // 6) PATCH atualiza parcialmente
    @PatchMapping("/{id}")
    public Person patch(@PathVariable Long id, @RequestBody Map<String, Object> fields) {

        return updatePerson.updatePerson(id, fields);
    }

    // 8) idade
    @GetMapping("/{id}/age")
    public Map<String, Object> age(@PathVariable Long id, @RequestParam String output) {
        System.out.println("ageeee");
        var out = AgeOutput.from(output);
        long value = agePerson.agePerson(id, out);
        System.out.println("age, out="+out+" value="+value);
        return Map.of("id", id, "output", out.name(), "value", value);
    }

    // 9) salário
    @GetMapping("/{id}/salary")
    public Map<String, Object> salary(@PathVariable Long id, @RequestParam String output) {
        System.out.println("salaryyyyyyyyyyyy");
        var out = SalaryOutput.from(output);
        BigDecimal value = salaryPerson.salaryPerson(id, out);
        return Map.of("id", id, "output", out.name(), "value", value);
    }
}
