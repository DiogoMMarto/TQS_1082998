package tqs.homework.busbook.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

import java.util.Objects;

@Entity
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String busName;

    private int numberOfSeats;
    private int availableSeats;

    private double price;

    private List<Boolean> seatMap; // Mapping of seats: seat number -> availability

    private String startingCity;
    private String endCity;

    private LocalDate date;
    private LocalTime duration;


    @PrePersist
    private void initializeSeatMap() {
        seatMap = new java.util.ArrayList<Boolean>(numberOfSeats);
        availableSeats = numberOfSeats;
    }


    public Connection() {
    }

    public Connection(Long id, String busName, int numberOfSeats, int availableSeats, double price, List<Boolean> seatMap, String startingCity, String endCity, LocalDate date, LocalTime duration) {
        this.id = id;
        this.busName = busName;
        this.numberOfSeats = numberOfSeats;
        this.availableSeats = availableSeats;
        this.price = price;
        this.seatMap = seatMap;
        this.startingCity = startingCity;
        this.endCity = endCity;
        this.date = date;
        this.duration = duration;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusName() {
        return this.busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public int getNumberOfSeats() {
        return this.numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getAvailableSeats() {
        return this.availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Boolean> getSeatMap() {
        return this.seatMap;
    }

    public void setSeatMap(List<Boolean> seatMap) {
        this.seatMap = seatMap;
    }

    public String getStartingCity() {
        return this.startingCity;
    }

    public void setStartingCity(String startingCity) {
        this.startingCity = startingCity;
    }

    public String getEndCity() {
        return this.endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getDuration() {
        return this.duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Connection)) {
            return false;
        }
        Connection connection = (Connection) o;
        return Objects.equals(id, connection.id) && Objects.equals(busName, connection.busName) && numberOfSeats == connection.numberOfSeats && availableSeats == connection.availableSeats && price == connection.price && Objects.equals(seatMap, connection.seatMap) && Objects.equals(startingCity, connection.startingCity) && Objects.equals(endCity, connection.endCity) && Objects.equals(date, connection.date) && Objects.equals(duration, connection.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, busName, numberOfSeats, availableSeats, price, seatMap, startingCity, endCity, date, duration);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", busName='" + getBusName() + "'" +
            ", numberOfSeats='" + getNumberOfSeats() + "'" +
            ", availableSeats='" + getAvailableSeats() + "'" +
            ", price='" + getPrice() + "'" +
            ", seatMap='" + getSeatMap() + "'" +
            ", startingCity='" + getStartingCity() + "'" +
            ", endCity='" + getEndCity() + "'" +
            ", date='" + getDate() + "'" +
            ", duration='" + getDuration() + "'" +
            "}";
    }

    
}
