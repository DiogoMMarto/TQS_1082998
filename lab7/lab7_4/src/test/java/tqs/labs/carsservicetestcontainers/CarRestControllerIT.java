package tqs.labs.carsservicetestcontainers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import tqs.labs.carsservicetestcontainers.model.Car;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CarRestControllerIT {

    @Autowired
    private TestRestTemplate rest;

    @LocalServerPort
    int randomServerPort;

    private String uri;

    @BeforeEach
    void setup(){
        uri = "http://localhost:" + randomServerPort + "/api/";
    }

    @Container
	public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:12")
		.withUsername("diogo")
		.withPassword("password")
		.withDatabaseName("test");

    @DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.password", container::getPassword);
		registry.add("spring.datasource.username", container::getUsername);
	}

    @Test
     void whenValidInput_thenCreateCar() {
        Car e30 = new Car("bmw", "e30");

        given()
            .contentType("application/json")
            .body(e30)
        .when()
            .post(uri+"cars")
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
     void givenCars_whenGetCars_thenStatus200()  {
        List<Car> response = 
            given()
            .when()
                .get(uri+"cars")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("model", hasItems("Camry"))
                .extract().body().jsonPath().getList(".", Car.class);
    }

    @Test
    void givenCarId_whenGetCars_thenStatusOk(){       
        given()
        .when()
            .get(uri+"cars/1")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("model", equalTo("Camry"));
    }

    @Test
    void givenInvalidCarId_whenGetCars_thenStatus404(){
        given()
        .when()
            .get(uri+"cars/-99")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());   
    }
}
