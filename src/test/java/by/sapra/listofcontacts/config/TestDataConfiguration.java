package by.sapra.listofcontacts.config;

import by.sapra.listofcontacts.testUtils.TestDbFacade;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestDataConfiguration {
    @Bean
    public TestDbFacade testDbFacade() {
        return new TestDbFacade();
    }
}
