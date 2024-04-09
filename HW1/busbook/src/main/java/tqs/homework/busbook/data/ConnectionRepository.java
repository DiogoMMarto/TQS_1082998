package tqs.homework.busbook.data;

import tqs.homework.busbook.model.Connection;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long>{

        List<Connection> findByDateBetweenAndStartingCityStartingWithAndEndCityStartingWithAndAvailableSeatsGreaterThan(LocalDate date1,
                LocalDate date2, String startingCity, String endCity, int i);

    
        @Query("SELECT DISTINCT c.endCity FROM Connection c")
        List<String> findDistinctEndCityBy();

        @Query("SELECT DISTINCT c.startingCity FROM Connection c")
        List<String> findDistinctStartingCityBy();


        Connection findById(int i);
}
