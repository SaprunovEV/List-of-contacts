package by.sapra.listofcontacts.services;

import by.sapra.listofcontacts.services.model.ContactList;
import by.sapra.listofcontacts.services.model.ContactModel;
import by.sapra.listofcontacts.services.model.ContactPayload;
import by.sapra.listofcontacts.services.model.RemovedId;

public interface ContactService {
    ContactList getAll();

    ContactModel getContactById(String id);

    ContactModel editContact(ContactPayload payload);

    ContactModel createContact(ContactPayload payload);

    RemovedId deleteContactById(String id);
}
