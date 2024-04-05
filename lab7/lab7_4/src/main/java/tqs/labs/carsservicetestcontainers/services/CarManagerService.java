package tqs.labs.carsservicetestcontainers.services;

import org.springframework.stereotype.Service;

import tqs.labs.carsservicetestcontainers.data.CarRepository;
import tqs.labs.carsservicetestcontainers.model.Car;

import java.util.List;
import java.util.Optional;

@Service
public class CarManagerService {

    final
    CarRepository carRepository;

    public CarManagerService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    public Car save(Car oneCar) {
        return carRepository.save(oneCar);
    }

    public List<Car> getAllCars() {

        return carRepository.findAll();
    }

    public Optional<Car> getCarDetails(Long carId) {
        
        Car c = carRepository.findByCarId(carId);
        return (c != null)? Optional.of( c ) : Optional.empty();
    }
}
