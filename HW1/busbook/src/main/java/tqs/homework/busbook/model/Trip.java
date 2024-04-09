package tqs.homework.busbook.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "connection_id")
    private Connection connection;

    @Enumerated(EnumType.STRING)
    private TripStatus status;

    private int assignedSeatNumber;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    @JsonBackReference
    private Reservation reservation;


    public Trip() {
    }

    public Trip(Long id, Connection connection, TripStatus status, int assignedSeatNumber, Reservation reservation) {
        this.id = id;
        this.connection = connection;
        this.status = status;
        this.assignedSeatNumber = assignedSeatNumber;
        this.reservation = reservation;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public TripStatus getStatus() {
        return this.status;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }

    public int getAssignedSeatNumber() {
        return this.assignedSeatNumber;
    }

    public void setAssignedSeatNumber(int assignedSeatNumber) {
        this.assignedSeatNumber = assignedSeatNumber;
    }

    public Reservation getReservation() {
        return this.reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Trip)) {
            return false;
        }
        Trip trip = (Trip) o;
        return Objects.equals(id, trip.id) && Objects.equals(connection, trip.connection) && Objects.equals(status, trip.status) && assignedSeatNumber == trip.assignedSeatNumber && Objects.equals(reservation, trip.reservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, connection, status, assignedSeatNumber, reservation);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", connection='" + getConnection() + "'" +
            ", status='" + getStatus() + "'" +
            ", assignedSeatNumber='" + getAssignedSeatNumber() + "'" +
            ", reservation='" + getReservation() + "'" +
            "}";
    }
   
}
