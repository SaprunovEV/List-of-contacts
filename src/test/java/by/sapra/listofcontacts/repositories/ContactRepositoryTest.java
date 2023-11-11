package by.sapra.listofcontacts.repositories;

import by.sapra.listofcontacts.config.AbstractDataIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = ContactRepositoryConf.class)
class ContactRepositoryTest extends AbstractDataIntegrationTest {
    @Autowired
    ContactRepository contactRepository;

    @Test
    void shouldDoSomething() throws Exception {

    }
}