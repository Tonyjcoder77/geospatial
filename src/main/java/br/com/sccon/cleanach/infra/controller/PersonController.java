package br.com.sccon.cleanach.infra.controller;

import br.com.sccon.cleanach.application.usecases.*;

import javax.validation.Valid;

import br.com.sccon.cleanach.domain.Person;
import br.com.sccon.cleanach.domain.enums.AgeOutput;
import br.com.sccon.cleanach.domain.enums.SalaryOutput;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

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

    @GetMapping
    public Map<Long, Person> list() {
        return listAllPersons.listOfPersons();
    }

    @GetMapping("/{id}")
    public Person get(@PathVariable Long id) {
        return listOnePerson.listOfPerson(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person personCriation(@Valid @RequestBody Person body) {
        return createPerson.creationOfPerson(body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {

        deletePerson.removePerson(id);
    }

    @PutMapping("/{id}")
    public Person replace(@PathVariable Long id, @Valid @RequestBody Person body) {

        return replacePerson.replacePerson(id, body);
    }

    @PatchMapping("/{id}")
    public Person patch(@PathVariable Long id, @RequestBody Map<String, Object> fields) {

        return updatePerson.updatePerson(id, fields);
    }

    @GetMapping("/{id}/age")
    public Map<String, Object> age(@PathVariable Long id, @RequestParam String output) {
        var out = AgeOutput.from(output);
        long value = agePerson.agePerson(id, out);
        System.out.println("age, out="+out+" value="+value);
        return Map.of("id", id, "output", out.name(), "value", value);
    }

    @GetMapping("/{id}/salary")
    public Map<String, Object> salary(@PathVariable Long id, @RequestParam String output) {
        var out = SalaryOutput.from(output);
        BigDecimal value = salaryPerson.salaryPerson(id, out);
        return Map.of("id", id, "output", out.name(), "value", value);
    }
}
