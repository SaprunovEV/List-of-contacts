package by.sapra.listofcontacts.testUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.support.TransactionTemplate;

public class TestDbFacade {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public <T> T find(Object id, Class<T> entityClass) {
        return transactionTemplate.execute(status -> testEntityManager.find(entityClass, id));
    }

    public <T> T save(TestDataBuilder<T> builder) {
        return transactionTemplate.execute(status -> testEntityManager.persistAndFlush(builder.build()));
    }

    public <T> TestDataBuilder<T> persistOnce(TestDataBuilder<T> builder) {
        return new TestDataBuilder<T>() {
            private T entity;

            @Override
            public T build() {
                if (entity == null) entity = persisted(builder).build();
                return entity;
            }
        };
    }

    public <T> TestDataBuilder<T> persisted(TestDataBuilder<T> builder) {
        return () -> transactionTemplate.execute(status -> {
            final T entity = builder.build();
            testEntityManager.persistAndFlush(entity);
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
