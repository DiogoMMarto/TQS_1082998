package tqs.labs.carsservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import tqs.labs.carsservice.data.CarRepository;
import tqs.labs.carsservice.model.Car;

/**
 * DataJpaTest limits the test scope to the data access context (no web environment loaded, for example)
 * tries to autoconfigure the database, if possible (e.g.: in memory db)
 */
@DataJpaTest
class C_CarRepositoryTest {

    // get a test-friendly Entity Manager
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository CarRepository;

    @Test
    void whenFinde30ById_thenReturne30Car() {
        // arrange a new Car and insert into db
        Car e30 = new Car("bmw", "e30");
        entityManager.persistAndFlush(e30); //ensure data is persisted at this point

        // test the query method of interest
        Car found = CarRepository.findByCarId(e30.getCarId());
        assertThat(found).isEqualTo(e30);
    }

    @Test
    void whenInvalidCarId_thenReturnNull() {
        Car fromDb = CarRepository.findByCarId(-99L);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Car e30 = new Car("bmw", "e30");
        Car corsa = new Car("opel", "corsa");
        Car golf = new Car("volkswagen", "golf");

        entityManager.persist(e30);
        entityManager.persist(corsa);
        entityManager.persist(golf);
        entityManager.flush();

        List<Car> allCars = CarRepository.findAll();

        assertThat(allCars).hasSize(3).extracting(Car::getModel).contains(e30.getModel(), golf.getModel(), corsa.getModel());
    }

}