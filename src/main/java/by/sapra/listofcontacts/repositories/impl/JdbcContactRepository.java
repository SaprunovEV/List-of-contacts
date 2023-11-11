package by.sapra.listofcontacts.repositories.impl;

import by.sapra.listofcontacts.exceptions.ContactNotFoundException;
import by.sapra.listofcontacts.model.ContactEntity;
import by.sapra.listofcontacts.model.mappers.ContactRowMapper;
import by.sapra.listofcontacts.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class JdbcContactRepository implements ContactRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<ContactEntity> deleteById(Long id) {
        Optional<ContactEntity> optional = findById(id);
        if (optional.isPresent()) {
            String sql = "DELETE FROM contact WHERE id = ?";
            jdbcTemplate.update(sql, id);
            return optional;
        }
        return Optional.empty();
    }

    @Override
    public Optional<ContactEntity> findById(Long id) {
        String sql = "SELECT * FROM contact WHERE id = ?";
        ContactEntity contactEntity = DataAccessUtils.singleResult(jdbcTemplate.query(
                sql,
                new ArgumentPreparedStatementSetter(new Object[]{id}),
                new RowMapperResultSetExtractor<>(new ContactRowMapper())
        ));
        return Optional.ofNullable(contactEntity);
    }

    @Override
    public List<ContactEntity> findAll() {
        String sql = "SELECT * FROM contact";
        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    @Override
    public ContactEntity save(ContactEntity entity2save) {
        if (entity2save.getId() == null){
            String sql = "INSERT INTO contact (id, first_name, last_name, email, phone) VALUES (?, ?, ?, ?, ?)";
            entity2save.setId(System.currentTimeMillis());
            jdbcTemplate.update(sql, entity2save.getId(), entity2save.getFirstName(), entity2save.getLastName(), entity2save.getEmail(), entity2save.getPhone());
            return entity2save;
        }

        Optional<ContactEntity> optional = findById(entity2save.getId());
        if (optional.isPresent()) {
            String sql = "UPDATE contact SET first_name = ?, last_name = ?, email = ?, phone = ? WHERE id = ?";
            jdbcTemplate.update(sql, entity2save.getFirstName(), entity2save.getLastName(), entity2save.getEmail(), entity2save.getPhone(), entity2save.getId());
            return entity2save;
        }

        throw new ContactNotFoundException("Cant update contact with id = " + entity2save.getId() + " not fount contact!");
    }
}
