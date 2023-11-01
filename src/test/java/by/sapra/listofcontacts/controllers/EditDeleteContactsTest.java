package by.sapra.listofcontacts.controllers;

import by.sapra.listofcontacts.services.ContactService;
import by.sapra.listofcontacts.services.model.ContactModel;
import by.sapra.listofcontacts.services.model.ContactPayload;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class EditDeleteContactsTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ContactService contactService;

    @Test
    void shouldReturnOkFromEditContact() throws Exception {
        String id = "1";
        prepareTest(id);

        mockMvc.perform(get("/contact/edit/{id}", 1))
                .andExpect(view().name("edit"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHaveCorrectTitle() throws Exception {
        String id = "1";
        prepareTest(id);

        mockMvc.perform(get("/contact/edit/{id}", id))
                .andExpect(content().string(containsString("Edit the contact " + id)));
    }

    @Test
    void shouldHaveModelOfContact() throws Exception {
        String id = "1";
        ContactModel contactModel = prepareTest(id);

        mockMvc.perform(get("/contact/edit/{id}", id))
                .andExpect(model().attribute("contact", contactModel));

        verify(contactService, times(1)).getContactById(id);
    }

    @Test
    void shouldRedirectToContactsPage() throws Exception {
        ContactPayload payload = getPayload();

        mockMvc.perform(postRequest("/contact/edit", payload))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/contacts"));

        verify(contactService, times(1)).editContact(payload);
    }

    @NotNull
    private MockHttpServletRequestBuilder postRequest(String url, ContactPayload payload) {
        return post(url)
                .param("id", payload.getId())
                .param("firstName", payload.getFirstName())
                .param("lastName", payload.getLastName())
                .param("email", payload.getEmail())
                .param("phone", payload.getPhone());
    }

    private ContactPayload getPayload() {
        return ContactPayload.builder()
                .id("1")
                .firstName("first")
                .lastName("last")
                .email("test@email.test")
                .phone("+375291234567")
                .build();
    }

    private ContactModel prepareTest(String id) {
        ContactModel contactModel = ContactModel.builder()
                .id("1")
                .firstName("first")
                .lastName("last")
                .email("test@emai.test")
                .phone("+375291234567")
                .build();
        when(contactService.getContactById(id)).thenReturn(contactModel);
        return contactModel;
    }
}
