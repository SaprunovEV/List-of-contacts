package by.sapra.listofcontacts.controllers;

import by.sapra.listofcontacts.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contact")
@RequiredArgsConstructor
public class EditDeleteContactsController {
    private final ContactService contactService;

    @GetMapping("/edit/{id}")
    private String handleEditContact(
            @PathVariable(name = "id") String id,
            Model model
    ) {
        model.addAttribute("contact", contactService.getContactById(id));
        return "edit";
    }
}
