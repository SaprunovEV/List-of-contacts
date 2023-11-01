package by.sapra.listofcontacts.controllers;

import by.sapra.listofcontacts.services.ContactService;
import by.sapra.listofcontacts.services.model.ContactPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contact")
@RequiredArgsConstructor
public class CreateContactController {
    private final ContactService contactService;

    @PostMapping("/create")
    public String handleCreateContact(@ModelAttribute(name = "contact")ContactPayload payload) {
        contactService.createContact(payload);
        return "redirect:/contacts";
    }
}
