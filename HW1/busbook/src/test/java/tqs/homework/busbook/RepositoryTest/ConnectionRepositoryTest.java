package tqs.homework.busbook.RepositoryTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import tqs.homework.busbook.data.ConnectionRepository;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ConnectionRepositoryTest {
    
    @Container
	public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:15.6-alpine3.19")
		.withUsername("root")
		.withPassword("password")
		.withDatabaseName("bus");

    @DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.password", container::getPassword);
		registry.add("spring.datasource.username", container::getUsername);
	}

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ConnectionRepository connectionRepository;
    
    @Test
    @DisplayName("Test findDistinctEndCityBy")
    public void testFindDistinctEndCityBy() {
        assertTrue(connectionRepository.findDistinctEndCityBy().size() == 20);
    }

    @Test
    @DisplayName("Test findDistinctStartingCityBy")
    public void testFindDistinctStartingCityBy() {
        assertTrue(connectionRepository.findDistinctStartingCityBy().size() == 21);
    }

    @Test
    @DisplayName("Test findByDateBetweenAndStartingCityStartingWithAndEndCityStartingWithAndAvailableSeatsGreaterThan")
    public void testFindByDateBetweenAndStartingCityStartingWithAndEndCityStartingWithAndAvailableSeatsGreaterThan() {
        assertTrue(connectionRepository.findByDateBetweenAndStartingCityStartingWithAndEndCityStartingWithAndAvailableSeatsGreaterThan(LocalDate.of(2000,1,1), LocalDate.of(3000,1,1), "Miami", "Chicago", 0).size() == 1);
        assertTrue(connectionRepository.findByDateBetweenAndStartingCityStartingWithAndEndCityStartingWithAndAvailableSeatsGreaterThan(LocalDate.of(2000,1,1), LocalDate.of(3000,1,1), "city", "city", 0).size() == 0);
    }

}
