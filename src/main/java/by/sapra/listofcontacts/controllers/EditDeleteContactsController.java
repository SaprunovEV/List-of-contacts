package by.sapra.listofcontacts.controllers;

import by.sapra.listofcontacts.services.ContactService;
import by.sapra.listofcontacts.services.model.ContactPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/contact")
@RequiredArgsConstructor
public class EditDeleteContactsController {
    private final ContactService contactService;

    @GetMapping("/edit/{id}")
    public String handleEditContact(
            @PathVariable(name = "id") String id,
            Model model
    ) {
        model.addAttribute("contact", contactService.getContactById(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String handleEditContact(@ModelAttribute(name = "contact") ContactPayload payload) {
        contactService.editContact(payload);
        return "redirect:/contacts";
    }
}
