package by.sapra.listofcontacts;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TestListOfContactsApplication.class)
class ListOfContactsApplicationTests {

    @Test
    void contextLoads() {
    }

    @BeforeAll
    static void beforeAll() {

    }
}
