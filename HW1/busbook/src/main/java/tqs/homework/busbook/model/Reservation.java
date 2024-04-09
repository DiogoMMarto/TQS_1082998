package tqs.homework.busbook.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Trip> trips = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private LocalDate reservationDate;

    public Reservation() {
    }

    public Reservation(UUID id, Person person, List<Trip> trips, ReservationStatus status, LocalDate reservationDate) {
        this.id = id;
        this.person = person;
        this.trips = trips;
        this.status = status;
        this.reservationDate = reservationDate;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Trip> getTrips() {
        return this.trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public ReservationStatus getStatus() {
        return this.status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public LocalDate getReservationDate() {
        return this.reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Reservation)) {
            return false;
        }
        Reservation reservation = (Reservation) o;
        return Objects.equals(id, reservation.id) && Objects.equals(person, reservation.person) && Objects.equals(trips, reservation.trips) && Objects.equals(status, reservation.status) && Objects.equals(reservationDate, reservation.reservationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, person, trips, status, reservationDate);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", person='" + getPerson() + "'" +
            ", trips='" + getTrips() + "'" +
            ", status='" + getStatus() + "'" +
            ", reservationDate='" + getReservationDate() + "'" +
            "}";
    }
    
}
