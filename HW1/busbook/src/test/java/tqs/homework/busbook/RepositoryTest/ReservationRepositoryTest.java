package tqs.homework.busbook.RepositoryTest;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

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

import tqs.homework.busbook.data.ReservationRepository;
import tqs.homework.busbook.model.Person;
import tqs.homework.busbook.model.Reservation;
import tqs.homework.busbook.model.ReservationStatus;
import tqs.homework.busbook.model.Trip;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReservationRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReservationRepository reservationRepository;

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

    @Test
    @DisplayName("Test findById")
    public void testFindById() {

        Reservation reservation = new Reservation();
        reservation.setPerson(new Person(2l,"Alice"));
        reservation.setTrips(new ArrayList<Trip>());
        reservation.setStatus(ReservationStatus.TOPAY);
        entityManager.persistAndFlush(reservation);

        assertTrue(reservationRepository.findById(reservation.getId()).get().equals(reservation));
        assertTrue(reservationRepository.findById(UUID.randomUUID()).isEmpty());
    }
}
