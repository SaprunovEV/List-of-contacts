package by.sapra.listofcontacts.repositories;

import by.sapra.listofcontacts.config.AbstractDataIntegrationTest;
import by.sapra.listofcontacts.exceptions.ContactNotFoundException;
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

    @Test
    void shouldDeleteEntity() throws Exception {
        ContactEntity expected = getTestDbFacade().save(ContactEntityTestDataBuilder.aContact().withFirstName("first1").build());

        Optional<ContactEntity> actual = contactRepository.deleteById(expected.getId());

        assertNull(getTestDbFacade().find(expected.getId()));
    }

    @Test
    void shouldReturnDeletedOptionalWithDeletedEntity() throws Exception {
        ContactEntity expected = getTestDbFacade().save(ContactEntityTestDataBuilder.aContact().withFirstName("first1").build());

        Optional<ContactEntity> actual = contactRepository.deleteById(expected.getId());

        assertAll(() -> {
            assertTrue(actual.isPresent());
            assertEquals(expected, actual.get());
        });
    }

    @Test
    void shouldReturnEmptyOptionalIfEntityWillNotPresent() throws Exception {
        ContactEntity expected = getTestDbFacade().save(ContactEntityTestDataBuilder.aContact().withFirstName("first1").build());

        Optional<ContactEntity> actual = contactRepository.deleteById(expected.getId() + 1);

        assertTrue(actual.isEmpty());
    }

    @Test
    void shouldSaveEntity() throws Exception {
        ContactEntity expected = ContactEntityTestDataBuilder.aContact().build();

        ContactEntity actual = contactRepository.save(expected);

        assertAll(() -> {
            assertNotNull(getTestDbFacade().find(actual.getId()));
            expected.setId(actual.getId());
            assertEquals(expected, actual);
        });
    }

    @Test
    void shouldUpdateIfContactWillExist() throws Exception {
        ContactEntity oldContact = getTestDbFacade().save(ContactEntityTestDataBuilder.aContact().build());

        ContactEntity newContact = ContactEntityTestDataBuilder.aContact()
                .withFirstName("newFirstName")
                .build();

        newContact.setId(oldContact.getId());

        ContactEntity actual = contactRepository.save(newContact);

        assertAll(() -> {
            assertNotNull(actual);
            ContactEntity editContact = getTestDbFacade().find(oldContact.getId());
            assertNotNull(editContact);
            assertEquals(editContact, getTestDbFacade().find(actual.getId()));
        });
    }

    @Test
    void shouldThrowExceptionIfCantUpdateContact() throws Exception {
        ContactEntity badContact = ContactEntityTestDataBuilder.aContact()
                .withFirstName("newFirstName")
                .build();

        badContact.setId(System.currentTimeMillis());

        assertThrows(ContactNotFoundException.class, () -> contactRepository.save(badContact));
    }
}