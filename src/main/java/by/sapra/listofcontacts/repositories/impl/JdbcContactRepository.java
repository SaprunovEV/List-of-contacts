package by.sapra.listofcontacts.repositories.impl;

import by.sapra.listofcontacts.model.ContactEntity;
import by.sapra.listofcontacts.repositories.ContactRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcContactRepository implements ContactRepository {
    @Override
    public Optional<ContactEntity> deleteById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<ContactEntity> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<ContactEntity> findAll() {
        return null;
    }

    @Override
    public ContactEntity save(ContactEntity entity2save) {
        return null;
    }
}
