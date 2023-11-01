package by.sapra.listofcontacts.services;

import by.sapra.listofcontacts.services.model.ContactList;
import by.sapra.listofcontacts.services.model.ContactModel;
import by.sapra.listofcontacts.services.model.ContactPayload;

public interface ContactService {
    ContactList getAll();

    ContactModel getContactById(String id);

    void editContact(ContactPayload payload);

    void createContact(ContactPayload payload);
}
