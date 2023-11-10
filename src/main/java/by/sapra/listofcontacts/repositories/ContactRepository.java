package by.sapra.listofcontacts.repositories;

import by.sapra.listofcontacts.model.ContactEntity;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {
    Optional<ContactEntity> deleteById(Integer id);

    Optional<ContactEntity> findById(String id);

    List<ContactEntity> findAll();

    ContactEntity save(ContactEntity entity2save);
}
