package by.sapra.listofcontacts.services.impl;

import by.sapra.listofcontacts.repositories.ContactRepository;
import by.sapra.listofcontacts.services.ContactService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@TestConfiguration
@MockBean(classes = ContactRepository.class)
public class JdbcContactServiceConf {
    @Bean
    public ContactService contactService(ContactRepository repo) {
        return new JdbcContactService(repo);
    }
}
