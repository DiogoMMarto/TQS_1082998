package tqs.labs.carsservice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import tqs.labs.carsservice.data.CarRepository;
import tqs.labs.carsservice.model.Car;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase

// switch AutoConfigureTestDatabase with TestPropertySource to use a real database
// @TestPropertySource( locations = "application-integrationtest.properties")
class D_CarRestControllerIT {

    // will need to use the server port for the invocation url
    @LocalServerPort
    int randomServerPort;

    // a REST client that is test-friendly
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }


    @Test
     void whenValidInput_thenCreateCar() {
        Car e30 = new Car("bmw", "e30");
        ResponseEntity<Car> entity = restTemplate.postForEntity("/api/cars", e30, Car.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        List<Car> found = repository.findAll();
        assertThat(found).extracting(Car::getModel).containsOnly("e30");
    }

    @Test
     void givenCars_whenGetCars_thenStatus200()  {
        createTestCar("bmw", "e30");
        createTestCar("opel", "corsa");


        ResponseEntity<List<Car>> response = restTemplate
                .exchange("/api/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getModel).containsExactly("e30", "corsa");

    }

    @Test
    void givenCarId_whenGetCars_thenStatusOk(){
        createTestCar("bmw", "e30");

        ResponseEntity<Car> response = restTemplate
                .exchange("/api/cars/1", HttpMethod.GET, null, new ParameterizedTypeReference<Car>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getModel()).isEqualTo("e30");
    }

    @Test
    void givenInvalidCarId_whenGetCars_thenStatus404(){
        ResponseEntity<Car> response = restTemplate
                .exchange("/api/cars/-99", HttpMethod.GET, null, new ParameterizedTypeReference<Car>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);    
    }

    private void createTestCar(String maker, String model) {
        Car emp = new Car(maker, model);
        repository.saveAndFlush(emp);
    }

}
