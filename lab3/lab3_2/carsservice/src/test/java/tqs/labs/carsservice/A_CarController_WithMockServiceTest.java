package tqs.labs.carsservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tqs.labs.carsservice.boundary.CarController;
import tqs.labs.carsservice.model.Car;
import tqs.labs.carsservice.services.CarManagerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
class A_CarController_WithMockServiceTest {

    @Autowired
    private MockMvc mvc;    //entry point to the web framework

    @MockBean
    private CarManagerService service;

    @Test
    void whenPostCar_thenCreateCar( ) throws Exception {
        Car e30 = new Car("bmw", "e30");

        when( service.save(any()) ).thenReturn( e30);

        mvc.perform(
                post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(e30)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.model", is("e30")));

        verify(service, times(1)).save(any());

    }

    @Test
    void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {
        Car e30 = new Car("bmw", "e30");
        Car golf = new Car("volkswagen ", "golf");
        Car opel = new Car("opel", "corsa");

        List<Car> allCars = Arrays.asList(e30 , golf, opel);

        when( service.getAllCars()).thenReturn(allCars);

        mvc.perform(
                get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].model", is(e30.getModel())))
                .andExpect(jsonPath("$[1].model", is(golf.getModel())))
                .andExpect(jsonPath("$[2].model", is(opel.getModel())));

        verify(service, times(1)).getAllCars();
    }

    @Test
    void givenCarId_whenGetCars_thenReturnJson() throws Exception {
        Car e30 = new Car("bmw", "e30");
        Long e30_id = 1l;

        when( service.getCarDetails(any()) ).thenReturn(Optional.of(e30));

        mvc.perform(
            get("/api/cars/{id}",e30_id).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.model", is("e30")));

        verify(service, times(1)).getCarDetails(any());
    }

    @Test
    void givenInvalidCarId_whenGetCars_thenStatus404() throws Exception{
        when( service.getCarDetails(any()) ).thenReturn(Optional.empty());

        mvc.perform(
            get("/api/cars/{id}",-99L).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(service, times(1)).getCarDetails(any());
    }
}