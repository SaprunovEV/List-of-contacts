package by.sapra.listofcontacts.config;

import by.sapra.listofcontacts.testUtils.TestDbFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextHierarchy(
        {
                @ContextConfiguration(
                        initializers = PostgresInit.PostgresApplicationContextInitializer.class,
                        classes = TestDataConfiguration.class
                )
        }
)
public class AbstractDataIntegrationTest {

    @Autowired
    private TestDbFacade testDbFacade;

    @BeforeAll
    static void beforeAll() {
        PostgresInit.container.start();
    }

    public TestDbFacade getTestDbFacade() {
        return testDbFacade;
    }

    @AfterEach
    void tearDown() {
        testDbFacade.cleanDatabase();
    }
}
