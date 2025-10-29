package br.com.sccon.cleanach.config;

import br.com.sccon.cleanach.application.usecases.*;
import br.com.sccon.cleanach.infra.gateways.PersonRepositoryInMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioConfig {

    @Bean
    AgePerson agePerson(PersonRepositoryInMemory personRepository){
        return new AgePerson(personRepository);
    }

    @Bean
    CreatePerson createPerson(PersonRepositoryInMemory personRepository){
        return new CreatePerson(personRepository);
    }

    @Bean
    DeletePerson deletePerson(PersonRepositoryInMemory personRepository){
        return new DeletePerson(personRepository);
    }

    @Bean
    ListAllPersons listAllPersons(PersonRepositoryInMemory personRepository){
        return new ListAllPersons(personRepository);
    }

    @Bean
    ListOnePerson listOnePerson(PersonRepositoryInMemory personRepository){
        return new ListOnePerson(personRepository);
    }

    @Bean
    ReplacePerson replacePerson(PersonRepositoryInMemory personRepository){
        return new ReplacePerson(personRepository);
    }

    @Bean
    SalaryPerson salaryPerson(PersonRepositoryInMemory personRepository){
        return new SalaryPerson(personRepository);
    }

    @Bean
    UpdatePerson updatePerson(PersonRepositoryInMemory personRepository){
        return new UpdatePerson(personRepository);
    }

    @Bean
    PersonRepositoryInMemory personRepositoryInMemory(){
        return new PersonRepositoryInMemory();
    }

}
