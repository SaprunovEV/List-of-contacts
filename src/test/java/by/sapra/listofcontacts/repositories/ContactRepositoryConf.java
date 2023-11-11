package by.sapra.listofcontacts.repositories;

import by.sapra.listofcontacts.repositories.impl.JdbcContactRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ContactRepositoryConf {
    @Bean
    public ContactRepository contactRepository() {
        return new JdbcContactRepository();
    }
}
