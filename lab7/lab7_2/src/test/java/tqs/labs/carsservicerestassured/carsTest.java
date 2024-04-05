package tqs.labs.carsservicerestassured;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import tqs.labs.carsservicerestassured.boundary.CarController;
import tqs.labs.carsservicerestassured.model.Car;
import tqs.labs.carsservicerestassured.services.CarManagerService;

@WebMvcTest(CarController.class)
public class carsTest {
    
    @Autowired
    private MockMvc mvc;    //entry point to the web framework

    @MockBean
    private CarManagerService service;

    @BeforeEach
    void setup(){
        Car e30 = new Car("bmw", "e30");
        Car golf = new Car("volkswagen ", "golf");
        Car opel = new Car("opel", "corsa");

        List<Car> allCars = Arrays.asList(e30 , golf, opel);

        when( service.getAllCars()).thenReturn(allCars);
        when( service.save(any()) ).thenReturn( e30);
        when( service.getCarDetails(any()) ).thenReturn(Optional.of(e30));

        RestAssuredMockMvc.mockMvc(mvc);
    }
    
    @Test
    void whenPostCar_thenCreateCar( ) throws Exception {
        Car e30 = new Car("bmw", "e30");

        RestAssuredMockMvc
            .given()
                .contentType("application/json")
                .body(e30)
            .when()
                .post("/api/cars")
            .then()
                .statusCode(201) //created
                .body("model", equalTo("e30")); 

        verify(service, times(1)).save(any());

    }

    @Test
    void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {

        RestAssuredMockMvc
            .given()
            .when()
                .get("/api/cars")
            .then()
                .statusCode(200)
                .body("model",hasItems("e30","golf","corsa"));

        verify(service, times(1)).getAllCars();
    }

    @Test
    void givenCarId_whenGetCars_thenReturnJson() throws Exception {
        Long e30_id = 1l;

        RestAssuredMockMvc
            .given()
            .when()
                .get("/api/cars/"+e30_id)
            .then()
                .statusCode(200)
                .body("model",equalTo("e30"));


        verify(service, times(1)).getCarDetails(any());
    }

    @Test
    void givenInvalidCarId_whenGetCars_thenStatus404() throws Exception{
        when( service.getCarDetails(any()) ).thenReturn(Optional.empty());

        RestAssuredMockMvc
            .given()
            .when()
                .get("/api/cars/"+-99)
            .then()
                .statusCode(404);

        verify(service, times(1)).getCarDetails(any());
    }

}
