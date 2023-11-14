package by.sapra.listofcontacts.controllers;

import by.sapra.listofcontacts.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ContactsController {

    private final ContactService contactService;

    @GetMapping("/contacts")
    public String handleContactsList(Model model) {
        model.addAttribute("contactList", contactService.getAll());
        return "contacts";
    }

    @GetMapping("/contact/delete/{id}")
    public String handleDeleteContact(@PathVariable(name = "id") String id) {
        contactService.deleteContactById(id);
        return "redirect:/contacts";
    }
}
