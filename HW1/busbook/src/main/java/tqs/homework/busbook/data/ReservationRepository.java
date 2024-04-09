package tqs.homework.busbook.data;

import tqs.homework.busbook.model.Reservation;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID>{

    @NonNull
    Optional<Reservation> findById(@NonNull UUID uuid);
    
}
