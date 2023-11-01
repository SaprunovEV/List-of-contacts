package by.sapra.listofcontacts.services;

import by.sapra.listofcontacts.services.model.ContactList;
import by.sapra.listofcontacts.services.model.ContactModel;

public interface ContactService {
    ContactList getAll();

    ContactModel getContactById(String id);
}
