package tqs.homework.busbook.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;


import tqs.homework.busbook.data.ConnectionRepository;
import tqs.homework.busbook.data.PersonRepository;
import tqs.homework.busbook.data.ReservationRepository;
import tqs.homework.busbook.data.TripRepository;
import tqs.homework.busbook.model.Connection;
import tqs.homework.busbook.model.Person;
import tqs.homework.busbook.model.Reservation;
import tqs.homework.busbook.model.ReservationStatus;
import tqs.homework.busbook.model.Trip;
import tqs.homework.busbook.service.ReservationService;


@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    
    @Mock( lenient = true )
    private ReservationRepository reservationRepository;

    @Mock( lenient = true )
    private TripRepository tripRepository;

    @Mock( lenient = true )
    private ConnectionRepository connectionRepository;

    @Mock( lenient = true )
    private PersonRepository personRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    @DisplayName("Test getAllReservations")
    public void testGetAllReservations() {
        List<Reservation> reservations = List.of(new Reservation(), new Reservation());
        when(reservationRepository.findAll()).thenReturn(reservations);
        assertTrue(reservationService.getAllReservations() == reservations);
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test getReservationByUuid")
    public void testGetReservationByUuid() {

        Reservation reservation = new Reservation();
        UUID uuid = UUID.randomUUID();
        reservation.setId(uuid);
        when(reservationRepository.findById(uuid)).thenReturn(java.util.Optional.of(reservation));
        assertTrue(reservationService.getReservationByUuid(uuid) == reservation);
        verify(reservationRepository, times(1)).findById(uuid);
    }

    @Test
    @DisplayName("Test payment")
    public void testPayment() {
        Reservation reservation = new Reservation();
        UUID uuid = UUID.randomUUID();
        reservation.setId(uuid);

        when(reservationRepository.findById(uuid)).thenReturn(java.util.Optional.of(reservation));
        reservationService.payReservation(uuid, LocalDate.now());
        assertTrue(reservation.getStatus() == ReservationStatus.BOOKED && reservation.getReservationDate() != null);
        verify(reservationRepository, times(1)).findById(uuid);
        verify(reservationRepository, times(1)).save(reservation);

    }

    @Test
    @DisplayName("Test make reservation")
    public void testMakeReservation() {
        List<Boolean> seatMapList = new ArrayList<Boolean>();
        for (int i = 0; i < 10; i++) {
            seatMapList.add(true);
        }
        Connection connection1 = new Connection();
        connection1.setSeatMap(seatMapList);
        connection1.setAvailableSeats(10);
        connection1.setNumberOfSeats(10);

        List<Boolean> seatMapList2 = new ArrayList<Boolean>();
        for (int i = 0; i < 20; i++) {
            seatMapList2.add(true);
        }
        Connection connection2= new Connection();

        connection2.setSeatMap(seatMapList2);
        connection2.setAvailableSeats(20);
        connection2.setNumberOfSeats(20);

        Trip trip1 = new Trip();
        trip1.setConnection(connection1);
        trip1.setAssignedSeatNumber(1);

        Trip trip2 = new Trip();
        trip2.setConnection(connection2);
        trip2.setAssignedSeatNumber(2);

        // Create an ArgumentCaptor to capture the argument passed to save()
        ArgumentCaptor<Reservation> argumentCaptor = ArgumentCaptor.forClass(Reservation.class);
        
        // Define the behavior to capture the argument and return it
        when(reservationRepository.save(argumentCaptor.capture())).thenAnswer(invocation -> {
            return invocation.getArgument(0);
        });
            
        when(personRepository.findByName("Alice")).thenReturn(new Person(1l, "Alice"));
        Reservation reservation = reservationService.makeReservation(List.of(trip1, trip2), "Alice");

        assertTrue(reservation.getStatus() == ReservationStatus.TOPAY);
        assertTrue(reservation.getPerson().getName().equals("Alice"));
        assertTrue(reservation.getTrips().size() == 2);
        assertTrue(connection1.getAvailableSeats() == 9);
        assertTrue(connection2.getAvailableSeats() == 19);
        assertTrue(trip1.getReservation() == reservation);
        assertTrue(trip2.getReservation() == reservation);

        verify(reservationRepository, times(1)).save(reservation);
        verify(connectionRepository, times(1)).save(connection1);
        verify(connectionRepository, times(1)).save(connection2);
        verify(tripRepository, times(1)).save(trip1);
        verify(tripRepository, times(1)).save(trip2);
        verify(personRepository, times(1)).findByName("Alice");
    }

    @Test
    @DisplayName("Bind reservation to person")
    public void testBindReservationToPerson() {
        Reservation reservation = new Reservation();

        reservationService.saveReservation(reservation);
        reservationService.bindReservationToPerson(reservation, "Alice");

        assertTrue(reservation.getPerson().getName().equals("Alice"));

        verify(reservationRepository, times(2)).save(reservation);
        verify(personRepository, times(1)).findByName("Alice");

        when(personRepository.findByName("Alice")).thenReturn(new Person(1l, "Alice"));
        reservationService.bindReservationToPerson(reservation, "Alice");

        assertTrue(reservation.getPerson().getName().equals("Alice"));

        verify(reservationRepository, times(3)).save(reservation);
        verify(personRepository, times(2)).findByName("Alice");
        verify(personRepository, times(1)).save(any());

    }

    @Test
    @DisplayName("Test reservation price")
    public void testReservationPrice() {
        Connection connection1 = new Connection();
        connection1.setSeatMap(new ArrayList<Boolean>(10));
        connection1.setAvailableSeats(10);
        connection1.setPrice(1.0);

        Connection connection2= new Connection();
        connection2.setSeatMap(new ArrayList<Boolean>(20));
        connection2.setAvailableSeats(20);
        connection2.setPrice(1.0);

        Trip trip1 = new Trip();
        trip1.setConnection(connection1);
        trip1.setAssignedSeatNumber(1);

        Trip trip2 = new Trip();
        trip2.setConnection(connection2);
        trip2.setAssignedSeatNumber(2);

        Reservation reservation = new Reservation();
        reservation.setTrips(List.of(trip1, trip2));

        when(reservationRepository.findById(reservation.getId())).thenReturn(java.util.Optional.of(reservation));
        assertTrue(reservationService.getReservationPrice(reservation.getId()) == 2.0);

        verify(reservationRepository, times(1)).findById(reservation.getId());
    }
}
