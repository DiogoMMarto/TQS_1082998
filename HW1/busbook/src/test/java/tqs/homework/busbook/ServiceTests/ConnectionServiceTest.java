package tqs.homework.busbook.ServiceTests;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;


import tqs.homework.busbook.data.ConnectionRepository;
import tqs.homework.busbook.model.Connection;
import tqs.homework.busbook.service.ConnectionService;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ConnectionServiceTest {

    @InjectMocks
    ConnectionService connectionService;

    @Mock( lenient = true )
    ConnectionRepository connectionRepository;

    private List<Connection> connections;

    @BeforeEach
    void setUp() {
        Connection connection1 = new Connection( 1L, "bus1", 50, 50, 10.0, null, "city1", "city2", LocalDate.of(2000,10,1), LocalTime.of(1, 40) );
        Connection connection2 = new Connection( 2L, "bus2", 50, 50, 10.0, null, "city2", "city3", LocalDate.of(2000,10,2), LocalTime.of(1, 40) );
        Connection connection3 = new Connection( 3L, "bus3", 50, 50, 10.0, null, "city3", "city4", LocalDate.of(2000,10,3), LocalTime.of(1, 40) );  
        Connection connection4 = new Connection( 4L, "bus4", 50, 50, 10.0, null, "city1", "city2", LocalDate.of(2000,10,4), LocalTime.of(1, 40) );

        connections = new ArrayList<>();
        connections.add(connection1);
        connections.add(connection2);
        connections.add(connection3);
        connections.add(connection4);
    }

    @Test
    @DisplayName("Test getAllConnections")
    public void testGetAllConnections() {
        when(connectionRepository.findAll()).thenReturn(connections);
        assertThat(connectionService.getAllConnections()).isEqualTo(connections); 
        verify(connectionRepository,times(1)).findAll();
    }

    @Test
    @DisplayName("Test getConnectionById")
    public void testGetConnectionById() {
        when(connectionRepository.findById(1L)).thenReturn(java.util.Optional.of(connections.get(0)));
        assertThat(connectionService.getConnectionById(1L)).isEqualTo(connections.get(0));
        verify(connectionRepository,times(1)).findById(1L);
        when(connectionRepository.findById(-1L)).thenReturn(java.util.Optional.empty());
        assertThat(connectionService.getConnectionById(-1L)).isNull();
        verify(connectionRepository,times(1)).findById(-1L);
    }

    @Test
    @DisplayName("Test getConnectionsBetweenDatesAndCitiesAvailable")
    public void testGetConnectionsBetweenDatesAndCitiesAvailable() {
        when(connectionRepository.findByDateBetweenAndStartingCityStartingWithAndEndCityStartingWithAndAvailableSeatsGreaterThan(LocalDate.of(2000,10,1), LocalDate.of(2000,10,4), "city1", "city2", 0)).thenReturn(List.of(connections.get(0), connections.get(3)));
        assertThat(connectionService.getConnectionsBetweenDatesAndCitiesAvailable("2000-10-01", "2000-10-04", "city1", "city2")).isEqualTo(List.of(connections.get(0), connections.get(3)));
        verify(connectionRepository,times(1)).findByDateBetweenAndStartingCityStartingWithAndEndCityStartingWithAndAvailableSeatsGreaterThan(LocalDate.of(2000,10,1), LocalDate.of(2000,10,4), "city1", "city2", 0);
        
        when(connectionRepository.findByDateBetweenAndStartingCityStartingWithAndEndCityStartingWithAndAvailableSeatsGreaterThan(LocalDate.of(2000,1,1), LocalDate.of(3000,1,1), "city1", "city2", 0)).thenReturn(List.of(connections.get(0), connections.get(3)));
        assertThat(connectionService.getConnectionsBetweenDatesAndCitiesAvailable("", "", "city1", "city2")).isEqualTo(List.of(connections.get(0), connections.get(3)));
        verify(connectionRepository,times(1)).findByDateBetweenAndStartingCityStartingWithAndEndCityStartingWithAndAvailableSeatsGreaterThan(LocalDate.of(2000,1,1), LocalDate.of(3000,1,1), "city1", "city2", 0);
        
        when(connectionRepository.findByDateBetweenAndStartingCityStartingWithAndEndCityStartingWithAndAvailableSeatsGreaterThan(LocalDate.of(2000,10,1), LocalDate.of(2000,10,2), "city1", "city2", 0)).thenReturn(List.of(connections.get(0)));
        assertThat(connectionService.getConnectionsBetweenDatesAndCitiesAvailable("2000-10-01", "2000-10-02", "city1", "city2")).isEqualTo(List.of(connections.get(0)));
        verify(connectionRepository,times(1)).findByDateBetweenAndStartingCityStartingWithAndEndCityStartingWithAndAvailableSeatsGreaterThan(LocalDate.of(2000,10,1), LocalDate.of(2000,10,4), "city1", "city2", 0);

        when(connectionRepository.findByDateBetweenAndStartingCityStartingWithAndEndCityStartingWithAndAvailableSeatsGreaterThan(LocalDate.of(2000,10,1), LocalDate.of(2000,10,4), "city1", "city5", 0)).thenReturn(List.of());
        assertThat(connectionService.getConnectionsBetweenDatesAndCitiesAvailable("2000-10-01", "2000-10-04", "city1", "city5")).isEmpty();
        verify(connectionRepository,times(1)).findByDateBetweenAndStartingCityStartingWithAndEndCityStartingWithAndAvailableSeatsGreaterThan(LocalDate.of(2000,10,1), LocalDate.of(2000,10,4), "city1", "city5", 0);
    }

    @Test
    @DisplayName("Test getStartingCities")
    public void testGetStartingCities() {
        when(connectionRepository.findDistinctStartingCityBy()).thenReturn(List.of("city1", "city2", "city3"));
        assertThat(connectionService.getStartingCities()).isEqualTo(List.of("city1", "city2", "city3"));
        verify(connectionRepository,times(1)).findDistinctStartingCityBy();
    }

    @Test
    @DisplayName("Test getEndingCities")
    public void testGetEndingCities() {
        when(connectionRepository.findDistinctEndCityBy()).thenReturn(List.of("city2", "city3", "city4"));
        assertThat(connectionService.getEndingCities()).isEqualTo(List.of("city2", "city3", "city4"));
        verify(connectionRepository,times(1)).findDistinctEndCityBy();
    }

}
