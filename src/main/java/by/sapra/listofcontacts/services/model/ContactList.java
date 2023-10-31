package by.sapra.listofcontacts.services.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ContactList {
    private List<ContactModel> contacts;
}
