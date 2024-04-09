package tqs.homework.busbook.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tqs.homework.busbook.data.ConnectionRepository;
import tqs.homework.busbook.data.PersonRepository;
import tqs.homework.busbook.data.ReservationRepository;
import tqs.homework.busbook.data.TripRepository;
import tqs.homework.busbook.model.Reservation;
import tqs.homework.busbook.model.ReservationStatus;
import tqs.homework.busbook.model.Trip;
import tqs.homework.busbook.model.TripStatus;
import tqs.homework.busbook.model.Connection;
import tqs.homework.busbook.model.Person;

@Service
public class ReservationService {
    
    final PersonRepository personRepository;
    final ReservationRepository reservationRepository;
    final TripRepository tripRepository;
    final ConnectionRepository connectionRepository;

    public ReservationService(PersonRepository personRepository, ReservationRepository reservationRepository, TripRepository tripRepository, ConnectionRepository connectionRepository) {
        this.personRepository = personRepository;
        this.reservationRepository = reservationRepository;
        this.tripRepository = tripRepository;
        this.connectionRepository = connectionRepository;
    }

    // List all reservations
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Reservation by uuid
    public Reservation getReservationByUuid(UUID uuid) {
        return reservationRepository.findById(uuid).orElse(null);
    }

    // Save reservation
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    // Delete reservation 
    //@Transactional
    // public void deleteReservation(UUID uuid) {
    //     Reservation reservation = getReservationByUuid(uuid);

    //     // delete all trips 
    //     for(Trip trip : reservation.getTrips()){
    //         trip.getConnection().getSeatMap().add(trip.getAssignedSeatNumber(), true);
    //         tripRepository.delete(trip);
    //     }

    //     reservationRepository.delete(reservation);
    // }

    // Pay reservation
    public void payReservation(UUID uuid, LocalDate date) {
        Reservation reservation = getReservationByUuid(uuid);
        reservation.setStatus(ReservationStatus.BOOKED);
        reservation.setReservationDate(date);
        reservationRepository.save(reservation);
    }

    // Make reservation based on list of trips and person name
    @Transactional
    public Reservation makeReservation(List<Trip> trips, String name) {
        Reservation reservation = new Reservation();

        reservation.setPerson(personRepository.findByName(name));
        reservation.setStatus(ReservationStatus.TOPAY);
        reservation.setTrips(trips);
        
        for (Trip trip : trips) {
            
            Connection connection = trip.getConnection();
            int remainingSeats = connection.getAvailableSeats();
            List<Boolean> availabilityMap = connection.getSeatMap();

            if (remainingSeats > 0 && availabilityMap.get(trip.getAssignedSeatNumber()) == true){
                connection.setAvailableSeats(remainingSeats - 1);
                availabilityMap.add(trip.getAssignedSeatNumber(), false);
                connectionRepository.save(connection);
            } else {
                return null; // maybe throw error here
            }
        }

        Reservation savedReservation = reservationRepository.save(reservation);
        
        for (Trip trip : trips) {
            trip.setReservation(savedReservation);
            tripRepository.save(trip);
        }
        
        return savedReservation;
    }

    // bind reservation to person
    public Reservation bindReservationToPerson(Reservation reservation, String name) {
        Person person = personRepository.findByName(name);
        
        if (person == null) {
            person = new Person();
            person.setName(name);
            personRepository.save(person);
        }

        reservation.setPerson(person);
        return reservationRepository.save(reservation);
    }


    // make trip given a connection and seat
    public Trip makeTripNonPersist(Connection connection, int seat) {
        Trip trip = new Trip();
        trip.setConnection(connection);
        trip.setAssignedSeatNumber(seat);
        trip.setStatus(TripStatus.BOOKED);
        return trip;
    }

    // Reservation get price
    public double getReservationPrice(UUID uuid) {
        Reservation reservation = getReservationByUuid(uuid);
        double price = 0.0;
        for (Trip trip : reservation.getTrips()) {
            price += trip.getConnection().getPrice();
        }
        return price;
    }
}
