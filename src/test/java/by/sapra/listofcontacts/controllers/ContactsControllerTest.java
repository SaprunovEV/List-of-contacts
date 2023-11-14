package by.sapra.listofcontacts.controllers;

import by.sapra.listofcontacts.services.ContactService;
import by.sapra.listofcontacts.services.model.ContactList;
import by.sapra.listofcontacts.services.model.ContactModel;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class ContactsControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private ContactService contactService;

    @Test
    void shouldReturnOkFromContactsPage() throws Exception {
        ContactList contactList = ContactList.builder().contacts(new ArrayList<>()).build();
        when(contactService.getAll())
                .thenReturn(contactList);
        mockMvc.perform(get("/contacts"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHaveTitleOfContactsPage() throws Exception {
        ContactList contactList = ContactList.builder().contacts(new ArrayList<>()).build();
        when(contactService.getAll())
                .thenReturn(contactList);
        mockMvc.perform(get("/contacts"))
                .andExpect(view().name("contacts"))
                .andExpect(content().string(containsString("All contacts")));
    }

    @Test
    void shouldHaveModelWithAllContacts() throws Exception {
        ContactList contactList = ContactList.builder().contacts(new ArrayList<>()).build();
        when(contactService.getAll())
                .thenReturn(contactList);

        mockMvc.perform(get("/contacts"))
                .andDo(print())
                .andExpect(model().attribute("contactList", contactList));

        verify(contactService, times(1)).getAll();
    }

    @Test
    void shouldViewAllContacts() throws Exception {
        ContactList contactList = ContactList.builder()
                .contacts(getContacts())
                .build();

        when(contactService.getAll())
                .thenReturn(contactList);

        mockMvc.perform(get("/contacts"))
                .andDo(print())
                .andExpect(content().string(containsString("<th>ID</th>")))
                .andExpect(content().string(containsString("<th>First name</th>")))
                .andExpect(content().string(containsString("<th>Last name</th>")))
                .andExpect(content().string(containsString("<th>Email</th>")))
                .andExpect(content().string(containsString("<th>Phone</th>")))
                .andExpect(content().string(containsString("<td>1</td>")))
                .andExpect(content().string(containsString("<a href=\"/contact/edit/1\">Edit</a>")))
                .andExpect(content().string(containsString("<a href=\"/contact/delete/1\">Delete</a>")))
                .andExpect(content().string(containsString("<td>2</td>")))
                .andExpect(content().string(containsString("<a href=\"/contact/edit/2\">Edit</a>")))
                .andExpect(content().string(containsString("<a href=\"/contact/delete/2\">Delete</a>")))
                .andExpect(content().string(containsString("<td>3</td>")))
                .andExpect(content().string(containsString("<a href=\"/contact/edit/3\">Edit</a>")))
                .andExpect(content().string(containsString("<a href=\"/contact/delete/3\">Delete</a>")))
                .andExpect(content().string(containsString("<td>4</td>")))
                .andExpect(content().string(containsString("<a href=\"/contact/edit/4\">Edit</a>")))
                .andExpect(content().string(containsString("<a href=\"/contact/delete/4\">Delete</a>")));
    }

    @Test
    void shouldHaveRefToCreateNewContact() throws Exception {
        ContactList contactList = ContactList.builder()
                .contacts(getContacts())
                .build();

        when(contactService.getAll())
                .thenReturn(contactList);

        mockMvc.perform(get("/contacts"))
                .andExpect(content().string(containsString("<a href=\"/contact/create\">Create</a>")));
    }

    @Test
    void shouldDeleteTheContactById() throws Exception {
        String id = "1";

        mockMvc.perform(get("/contact/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/contacts"));

        verify(contactService, times(1)).deleteContactById(id);
    }

    @NotNull
    private static List<ContactModel> getContacts() {
        return List.of(
                ContactModel.builder()
                        .id("1")
                        .firstName("first1")
                        .lastName("last1")
                        .email("email1@email.test")
                        .phone("+375291234561").build(),
                ContactModel.builder()
                        .id("2")
                        .firstName("first2")
                        .lastName("last2")
                        .email("email1@email.test")
                        .phone("+375291234561").build(),
                ContactModel.builder()
                        .id("3")
                        .firstName("first3")
                        .lastName("last3")
                        .email("email1@email.test")
                        .phone("+375291234561").build(),
                ContactModel.builder()
                        .id("4")
                        .firstName("first4")
                        .lastName("last4")
                        .email("email1@email.test")
                        .phone("+375291234561").build()
        );
    }
}
