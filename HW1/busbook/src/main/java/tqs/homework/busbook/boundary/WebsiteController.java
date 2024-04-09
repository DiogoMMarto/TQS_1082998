package tqs.homework.busbook.boundary;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import tqs.homework.busbook.model.Reservation;
import tqs.homework.busbook.model.Trip;
import tqs.homework.busbook.service.ConnectionService;
import tqs.homework.busbook.service.ExachangeRateService;
import tqs.homework.busbook.service.ReservationService;
import tqs.homework.busbook.util.Currency;

@Controller
public class WebsiteController {
    
    @Autowired
    ConnectionService connectionService;

    @Autowired
    ReservationService reservationService;

    ExachangeRateService exachangeRateService = new ExachangeRateService( new RestTemplate());

    @GetMapping
    String getFind(Model model) {
        model.addAttribute("sCities", connectionService.getStartingCities());
        model.addAttribute("dCities", connectionService.getEndingCities());
        return "find";
    }

    @GetMapping("/reserve")
    String getReserve(Model model,
                    @RequestParam("departureCity") String departureCity,
                    @RequestParam("destinationCity") String destinationCity,
                    @RequestParam("dateStart") String dateStart,
                    @RequestParam("dateEnd") String dateEnd,
                    @RequestParam(value = "returnTrip", required = false) boolean returnTrip) {
        
        model.addAttribute("departureCity", departureCity);
        model.addAttribute("destinationCity", destinationCity);
        model.addAttribute("dateStart", dateStart);
        model.addAttribute("dateEnd", dateEnd);
        model.addAttribute("returnTrip", returnTrip);
        model.addAttribute("connections1", connectionService.getConnectionsBetweenDatesAndCitiesAvailable( dateStart, dateEnd , departureCity, destinationCity));
        if(returnTrip)
            model.addAttribute("connections2", connectionService.getConnectionsBetweenDatesAndCitiesAvailable( dateEnd, dateStart , destinationCity, departureCity));

        return "reserve";
    }

    @PostMapping("/buy")
    String postBuy(Model model,
                @RequestParam("seat1") String seat1,
                @RequestParam(value = "seat2", required = false) String seat2
                    ) {
        
        List<Trip> trips = new ArrayList<>();

        String[] _help = seat1.split(",");
        long id1 = Long.parseLong(_help[1]);
        int seatNumber1 = Integer.parseInt(_help[0]);

        trips.add(
            reservationService.makeTripNonPersist(
                connectionService.getConnectionById(id1), 
                seatNumber1
        ));

        if(seat2 != null){
            String[] _helpr = seat2.split(",");
            long id2 = Long.parseLong(_helpr[1]);
            int seatNumber2 = Integer.parseInt(_helpr[0]);

            trips.add(
                reservationService.makeTripNonPersist(
                    connectionService.getConnectionById(id2), 
                    seatNumber2
            ));
        }

        Reservation reservation =  reservationService.makeReservation(trips, "");
        Map<Currency, Double> rates = exachangeRateService.getExchangeRatesUSD();

        model.addAttribute("trips", reservation.getTrips());
        model.addAttribute("rates", rates);
        model.addAttribute("totalPrice", reservationService.getReservationPrice(reservation.getId()));
        model.addAttribute("id", reservation.getId().toString());

        return "buy";
    }

    @PostMapping("/buy/confirm")
    String postBuyConfirm(Model model,
                @RequestParam("reservationId") String reservationId,
                @RequestParam("name") String name
                    ) {
        
        Reservation reservation = reservationService.getReservationByUuid(UUID.fromString(reservationId));
        reservation = reservationService.bindReservationToPerson(reservation, name);

        model.addAttribute("totalPrice", reservationService.getReservationPrice(reservation.getId()));
        model.addAttribute("id", reservation.getId().toString());
        model.addAttribute("name", name);
        model.addAttribute("date", LocalDate.now());

        return "buy_confirm";
    }

    @PostMapping("/buy/finish")
    String postBuyFinish(Model model,
                @RequestParam("id") String id,
                @RequestParam("date") String date
                    ) {
  
        reservationService.payReservation(UUID.fromString(id), LocalDate.parse(date));

        Reservation reservation = reservationService.getReservationByUuid(UUID.fromString(id));

        model.addAttribute("trips", reservation.getTrips());
        model.addAttribute("totalPrice", reservationService.getReservationPrice(reservation.getId()));
        model.addAttribute("name", reservation.getPerson().getName());
        model.addAttribute("status", reservation.getStatus());
        model.addAttribute("date", reservation.getReservationDate());

        return "details";
    }

    @GetMapping("/list")
    String getList(Model model) {
        model.addAttribute("reservations", reservationService.getAllReservations());
        return "list";
    }

    @GetMapping("/details")
    String getDetails(Model model,
                    @RequestParam("id") String id
                    ) {
        Reservation reservation = reservationService.getReservationByUuid(UUID.fromString(id));

        model.addAttribute("trips", reservation.getTrips());
        model.addAttribute("totalPrice", reservationService.getReservationPrice(reservation.getId()));
        model.addAttribute("name", reservation.getPerson().getName());
        model.addAttribute("status", reservation.getStatus());
        model.addAttribute("date", reservation.getReservationDate());

        return "details";
    }

}
