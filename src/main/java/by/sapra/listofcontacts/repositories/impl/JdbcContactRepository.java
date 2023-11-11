package by.sapra.listofcontacts.repositories.impl;

import by.sapra.listofcontacts.model.ContactEntity;
import by.sapra.listofcontacts.model.mappers.ContactRowMapper;
import by.sapra.listofcontacts.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcContactRepository implements ContactRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<ContactEntity> deleteById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<ContactEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<ContactEntity> findAll() {
        String sql = "SELECT * FROM contact";
        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    @Override
    public ContactEntity save(ContactEntity entity2save) {
        return null;
    }
}
