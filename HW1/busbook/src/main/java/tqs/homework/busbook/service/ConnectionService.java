package tqs.homework.busbook.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.homework.busbook.data.ConnectionRepository;
import tqs.homework.busbook.model.Connection;

@Service
public class ConnectionService {
    
    @Autowired
    ConnectionRepository connectionRepository;

    // List all connections
    public List<Connection> getAllConnections() {
        return connectionRepository.findAll();
    }

    // Connection by id
    public Connection getConnectionById(Long id) {
        return connectionRepository.findById(id).orElse(null);
    }
    
    // List all connections between date1 and date2 ,
    // with starting city like and ending city like
    // and number of available seats greater than 0
    public List<Connection> getConnectionsBetweenDatesAndCitiesAvailable(String date1, String date2, String startingCity, String endCity) {
        try {
            LocalDate _date1 = LocalDate.parse(date1);
            LocalDate _date2 = LocalDate.parse(date2);

            return connectionRepository.findByDateBetweenAndStartingCityStartingWithAndEndCityStartingWithAndAvailableSeatsGreaterThan(_date1, _date2, startingCity, endCity,0);
        } catch (Exception e) {
            return connectionRepository.findByDateBetweenAndStartingCityStartingWithAndEndCityStartingWithAndAvailableSeatsGreaterThan(LocalDate.of(2000,1,1), LocalDate.of(3000,1,1), startingCity, endCity,0);
        }
    }

    // List all starting cities
    public List<String> getStartingCities() {
        return connectionRepository.findDistinctStartingCityBy();
    }

    // List all ending cities
    public List<String> getEndingCities() {
        return connectionRepository.findDistinctEndCityBy();
    }


}
