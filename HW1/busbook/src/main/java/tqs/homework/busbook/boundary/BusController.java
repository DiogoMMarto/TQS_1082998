package tqs.homework.busbook.boundary;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tqs.homework.busbook.model.Connection;
import tqs.homework.busbook.model.Reservation;
import tqs.homework.busbook.model.Trip;
import tqs.homework.busbook.service.ConnectionService;
import tqs.homework.busbook.service.ReservationService;

@RestController
@RequestMapping("/api")
public class BusController {
    
    @Autowired
    ConnectionService connectionService;

    @Autowired
    ReservationService reservationService;

    // Get all connections
    @GetMapping(path = "/connections" , produces = "application/json")
    public List<Connection> getAllConnections() {
      return connectionService.getAllConnections();  
    }

    // Get connection by id
    @GetMapping(path = "/connections/{id}" , produces = "application/json")
    public Connection getConnectionById(String id) {
      return connectionService.getConnectionById(Long.parseLong(id));
    }

    // Buy reservation
    @PostMapping(path = "/buy" , produces = "application/json")
    public Reservation postBuy(@RequestParam("seat1") String seat1 , @RequestParam("name") String name) {

      List<Trip> trips = new ArrayList<>();

      String[] _help = seat1.split(",");
      long id1 = Long.parseLong(_help[1]);
      int seatNumber1 = Integer.parseInt(_help[0]);

      trips.add(
          reservationService.makeTripNonPersist(
              connectionService.getConnectionById(id1), 
              seatNumber1
      ));
      
      Reservation reservation = reservationService.makeReservation(trips, name);
      reservation = reservationService.bindReservationToPerson(reservation, name);

      return reservation;
    }

    // pay reservation
    @PostMapping(path = "/pay" , produces = "application/json")
    public Reservation postPay(@RequestParam("id") String id) {
      reservationService.payReservation(UUID.fromString(id), LocalDate.now());
      return reservationService.getReservationByUuid(UUID.fromString(id));
    }

    // Get all reservations
    @GetMapping(path = "/reservations" , produces = "application/json")
    public List<Reservation> getAllReservations() {
      return reservationService.getAllReservations();
    }

    // Get reservation by id
    @GetMapping(path = "/reservations/{id}" , produces = "application/json")
    public Reservation getReservationById(String id) {
      return reservationService.getReservationByUuid(UUID.fromString(id));
    }
}
