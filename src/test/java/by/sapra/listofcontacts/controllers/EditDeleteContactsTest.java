package by.sapra.listofcontacts.controllers;

import by.sapra.listofcontacts.services.ContactService;
import by.sapra.listofcontacts.services.model.ContactModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
                .andDo(print())
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
                .andDo(print())
                .andExpect(model().attribute("contact", contactModel));

        verify(contactService, times(1)).getContactById(id);
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
