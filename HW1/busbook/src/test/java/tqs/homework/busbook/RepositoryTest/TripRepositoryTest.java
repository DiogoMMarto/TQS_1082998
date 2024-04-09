package tqs.homework.busbook.RepositoryTest;

import static org.junit.jupiter.api.Assertions.assertTrue;


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
import tqs.homework.busbook.data.TripRepository;
import tqs.homework.busbook.model.Trip;


@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TripRepositoryTest {

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
    private TripRepository tripRepository;

	@Autowired
	private ConnectionRepository connectionRepository;
    
	@Test
	@DisplayName("Test save trip")
	public void testSaveTrip() {
		Trip trip = new Trip();
		trip.setAssignedSeatNumber(1);
		trip.setConnection(connectionRepository.findById(1));

		entityManager.persistAndFlush(trip);
		assertTrue(tripRepository.findById(trip.getId()).get().equals(trip));
	}

}
