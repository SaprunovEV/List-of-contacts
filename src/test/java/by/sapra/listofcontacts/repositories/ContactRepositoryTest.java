package by.sapra.listofcontacts.repositories;

import by.sapra.listofcontacts.config.AbstractDataIntegrationTest;
import by.sapra.listofcontacts.model.ContactEntity;
import by.sapra.listofcontacts.testUtils.ContactEntityTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = ContactRepositoryConf.class)
class ContactRepositoryTest extends AbstractDataIntegrationTest {
    @Autowired
    ContactRepository contactRepository;

    @Test
    void shouldEmptyListIfDatabaseIsEmpty() throws Exception {
        List<ContactEntity> actual = contactRepository.findAll();

        assertTrue(actual.isEmpty());
    }

    @Test
    void shouldNotReturnEmptyListIfDatabaseWillNotEmpty() throws Exception {
        ContactEntity first1 = getTestDbFacade().save(ContactEntityTestDataBuilder.aContact().withFirstName("first1").build());
        ContactEntity first2 = getTestDbFacade().save(ContactEntityTestDataBuilder.aContact().withFirstName("first2").build());
        ContactEntity first3 = getTestDbFacade().save(ContactEntityTestDataBuilder.aContact().withFirstName("first3").build());
        ContactEntity first4 = getTestDbFacade().save(ContactEntityTestDataBuilder.aContact().withFirstName("first4").build());

        List<Object> expected = List.of(first1, first2, first3, first4);

        List<ContactEntity> actual = contactRepository.findAll();

        assertAll(() -> {
            assertFalse(actual.isEmpty());
            actual.forEach(contact -> assertTrue(expected.contains(contact)));
        });
    }

    @Test
    void shouldReturnNotEmptyOptionalIfDatabaseHaveEntityWithId() throws Exception {
        ContactEntity expected = getTestDbFacade().save(ContactEntityTestDataBuilder.aContact().withFirstName("first1").build());

        Optional<ContactEntity> actual = contactRepository.findById(expected.getId());

        assertTrue(actual.isPresent());
    }

    @Test
    void shouldReturnEmptyOptionalIfIdWillNonPresent() throws Exception {
        ContactEntity expected = getTestDbFacade().save(ContactEntityTestDataBuilder.aContact().withFirstName("first1").build());

        Optional<ContactEntity> actual = contactRepository.findById(expected.getId() + 1);

        assertTrue(actual.isEmpty());
    }

    @Test
    void shouldReturnCorrectEntityById() throws Exception {
        ContactEntity expected = getTestDbFacade().save(ContactEntityTestDataBuilder.aContact().withFirstName("first1").build());

        Optional<ContactEntity> actual = contactRepository.findById(expected.getId());

        assertEquals(expected, actual.get());
    }
}