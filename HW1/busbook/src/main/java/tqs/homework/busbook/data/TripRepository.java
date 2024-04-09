package tqs.homework.busbook.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.homework.busbook.model.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long>{
    
}
