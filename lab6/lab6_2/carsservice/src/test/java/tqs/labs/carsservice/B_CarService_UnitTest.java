package tqs.labs.carsservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import tqs.labs.carsservice.data.CarRepository;
import tqs.labs.carsservice.model.Car;
import tqs.labs.carsservice.services.CarManagerService;
/**
 * Test scenario: verify the logic of the Service, mocking the response of the datasource
 * Results in standard unit test with mocks
 */
@ExtendWith(MockitoExtension.class)
class B_CarService_UnitTest {

    // mocking the responses of the repository (i.e., no database will be used)
    // lenient is required because we load more expectations in the setup
    // than those used in some tests. As an alternative, the expectations
    // could move into each test method and be trimmed (no need for lenient then)
    @Mock( lenient = true)
    private CarRepository CarRepository;

    @InjectMocks
    private CarManagerService CarService;

    @BeforeEach
    public void setUp() {
        Car e30 = new Car("bmw", "e30");
        e30.setCarId(100L);

        Car corsa = new Car("opel", "corsa");
        Car golf = new Car("volkswagen", "golf");

        List<Car> allCars = Arrays.asList(e30, corsa, golf);

        Mockito.when(CarRepository.findByCarId(e30.getCarId())).thenReturn(e30);
        Mockito.when(CarRepository.findAll()).thenReturn(allCars);
        Mockito.when(CarRepository.findByCarId(-99L)).thenReturn(null);
    }

    @Test
     void whenSearchValidd_thenCarShouldBeFound() {
        Long id = 100L;
        Car found = CarService.getCarDetails(id).get();

        assertThat(found.getCarId()).isEqualTo(id);
    }

    @Test
     void whenSearchInvalidId_thenCarShouldNotBeFound() {
        Optional<Car> fromDb = CarService.getCarDetails(-99L);
        assertThat(fromDb).isEmpty();

        verify(CarRepository,times(1)).findByCarId(any());
    }

    @Test
     void given3Cars_whengetAll_thenReturn3Records() {
        Car e30 = new Car("bmw", "e30");
        Car corsa = new Car("opel", "corsa");
        Car golf = new Car("volkswagen", "golf");

        List<Car> allCars = CarService.getAllCars();
        verify(CarRepository,times(1)).findAll();
        assertThat(allCars).hasSize(3).extracting(Car::getModel).contains(e30.getModel(), golf.getModel(), corsa.getModel());
    }
}
