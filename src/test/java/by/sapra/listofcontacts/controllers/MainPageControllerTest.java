package by.sapra.listofcontacts.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MainPageController.class)
public class MainPageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnOkFromBaseUrl() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHaveTitle() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome to the app for contact list")));
    }

    @Test
    void shouldHaveLinksToAllContacts() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(content().string(containsString("<a href=\"/contacts\" >contacts</a>")));
    }
}
