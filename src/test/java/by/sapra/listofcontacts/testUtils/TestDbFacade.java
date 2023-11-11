package by.sapra.listofcontacts.testUtils;

import by.sapra.listofcontacts.model.ContactEntity;
import by.sapra.listofcontacts.model.mappers.ContactRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.CallableStatement;

public class TestDbFacade {
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ContactEntity find(Integer id) {
        return transactionTemplate.execute(status -> {
            String sql = "SELECT * FROM contact WHERE id = ?;";
            return DataAccessUtils.singleResult(
                    jdbcTemplate.query(
                            sql,
                            new ArgumentPreparedStatementSetter(new Object[] {id}),
                            new RowMapperResultSetExtractor<>(new ContactRowMapper() , 1)
                    )
            );
        });
    }

    public ContactEntity save(ContactEntity entity) {
        return transactionTemplate.execute(status -> {
            entity.setId(System.currentTimeMillis());

            String sql = "INSERT INTO contact (id, first_name, last_name, email, phone) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPhone());

            return entity;
        });
    }

    public void cleanDatabase() {
        transactionTemplate.execute(status -> {
            JdbcTestUtils.deleteFromTables(
                    jdbcTemplate,
                    "contact"
            );

            return null;
        });
    }
}
