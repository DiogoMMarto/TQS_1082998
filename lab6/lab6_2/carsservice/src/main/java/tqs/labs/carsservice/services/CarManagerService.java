package tqs.labs.carsservice.services;

import org.springframework.stereotype.Service;

import tqs.labs.carsservice.data.CarRepository;
import tqs.labs.carsservice.model.Car;

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
