package by.sapra.listofcontacts.services.impl;

import by.sapra.listofcontacts.model.ContactEntity;
import by.sapra.listofcontacts.repositories.ContactRepository;
import by.sapra.listofcontacts.services.ContactService;
import by.sapra.listofcontacts.services.model.ContactList;
import by.sapra.listofcontacts.services.model.ContactModel;
import by.sapra.listofcontacts.services.model.ContactPayload;
import by.sapra.listofcontacts.services.model.RemovedId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JdbcContactServiceConf.class)
public class JdbcContactServiceTest {
    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactRepository contactRepository;

    @Test
    void shouldNotReturnNull() throws Exception {

        ContactList contactList = contactService.getAll();

        assertAll(() -> {
            assertNotNull(contactList, "не должен возвращать null");
            assertNotNull(contactList.getContacts(), "список контактов не должен быть null");
        });
    }

    @Test
    void shouldReturnListOfAllContacts() throws Exception {
        List<ContactEntity> expected = createEntityList();

        when(contactRepository.findAll()).thenReturn(expected);

        ContactList actual = contactService.getAll();

        assertContactList(expected, actual.getContacts());

        verify(contactRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyContactListIfNobodyContactIsPresent() throws Exception {
        ArrayList<ContactEntity> expected = new ArrayList<>();
        when(contactRepository.findAll()).thenReturn(expected);

        ContactList actual = contactService.getAll();

        assertContactList(expected, actual.getContacts());

        verify(contactRepository, times(1)).findAll();
    }

    @Test
    void shouldCallDeleteMethodFromRepository() throws Exception {
        String id = "1";

        String firstName = "first";
        String lastName = "last";
        String email = "email@email.test";
        String phone = "+375291234567";

        when(contactRepository.deleteById(Integer.valueOf(id))).thenReturn(
                Optional.of(createEntity(id, firstName, lastName, email, phone))
        );

        RemovedId actual = contactService.deleteContactById(id);

        assertEquals(id, actual.getData());

        verify(contactRepository, times(1)).deleteById(Integer.valueOf(id));
    }

    @Test
    void shouldReturnNullModelIfContactNotFound() throws Exception {
        String id = "1";

        ContactModel actual = contactService.getContactById(id);

        when(contactRepository.findById(id)).thenReturn(Optional.empty());

        assertNull(actual, "возвращаемое значение должно быть null если репозиторий не нашел сущность");

        verify(contactRepository, times(1)).findById(id);
    }

    @Test
    void shouldReturnModelIfContactHaveBeenFound() throws Exception {
        String id = "1";

        String firstName = "first";
        String lastName = "last";
        String email = "email@email.test";
        String phone = "+375291234567";
        ContactEntity contactEntity = createEntity(id, firstName, lastName, email, phone);

        when(contactRepository.findById(id)).thenReturn(Optional.of(contactEntity));

        ContactModel actual = contactService.getContactById(id);

        assertAll(() -> {
            assertNotNull(actual, "возвращаемое значение не должно быть null если репозиторий нашел сущность");
            assertContactModel(contactEntity, actual);
        });
    }

    @Test
    void shouldSaveNewContact() throws Exception {
        String phone = "+375291234567";
        String email = "email@email.test";
        String lastName = "last";
        String firstName = "first";

        ContactPayload payload = ContactPayload.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .build();

        ContactEntity entity2save = mapPayloadToEntity(payload);
        entity2save.setId(null);

        ContactPayload tempPayload = ContactPayload.builder()
                .id("1")
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .build();

        ContactEntity expected = mapPayloadToEntity(tempPayload);
        when(contactRepository.save(entity2save)).thenReturn(expected);

        ContactModel actual = contactService.createContact(payload);

        assertContactModel(expected, actual);

        verify(contactRepository, times(1)).save(entity2save);
    }

    @Test
    void shouldEditContact() throws Exception {
        String id = "1";
        String phone = "+375291234567";
        String email = "email@email.test";
        String lastName = "last";
        String firstName = "first";

        ContactPayload payload = ContactPayload.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .build();

        ContactEntity expected = ContactEntity.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .id(Integer.valueOf(id))
                .build();
        when(contactRepository.save(mapPayloadToEntity(payload))).thenReturn(expected);

        ContactModel actual = contactService.editContact(payload);

        assertContactModel(expected, actual);

        verify(contactRepository, times(1)).save(expected);
    }

    private ContactEntity mapPayloadToEntity(ContactPayload payload) {
        return createEntity(payload.getId(), payload.getFirstName(), payload.getLastName(), payload.getEmail(), payload.getPhone());
    }

    private List<ContactEntity> createEntityList() {
        List<ContactEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(ContactEntity.builder()
                            .id(i)
                            .email("email" + i + "@email.test")
                            .firstName("first" + i)
                            .lastName("last" + i)
                            .phone("+375291234567")
                    .build());
        }
        return list;
    }

    private static ContactEntity createEntity(String id, String firstName, String lastName, String email, String phone) {
        return ContactEntity.builder()
                .id(id != null ? Integer.parseInt(id) : null)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .build();
    }

    private void assertContactList(List<ContactEntity> expected, List<ContactModel> actual) {
        assertAll(() -> {
            assertEquals(expected.size(), actual.size());
            expected.sort(Comparator.comparing(ContactEntity::getId));
            ArrayList<ContactModel> actualSort = new ArrayList<>(actual);
            actualSort.sort(Comparator.comparing(ContactModel::getId));
            for (int i = 0; i < actualSort.size(); i++) {
                assertContactModel(expected.get(0), actual.get(0));
            }
        });
    }

    private void assertContactModel(ContactEntity expected, ContactModel actual) {
        assertEquals(expected.getId(), Integer.valueOf(actual.getId()));
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getPhone(), expected.getPhone());
    }
}
