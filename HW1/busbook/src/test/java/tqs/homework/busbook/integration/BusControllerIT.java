package tqs.homework.busbook.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import tqs.homework.busbook.service.ConnectionService;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BusControllerIT {
    
    @LocalServerPort
    int randomServerPort;

    private String uri;

    @Container
	public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:15.6-alpine3.19")
		.withUsername("root")
		.withPassword("password")
		.withDatabaseName("bus");

    @DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.password", container::getPassword);
		registry.add("spring.datasource.username", container::getUsername);
	}

    @BeforeEach
    void setup(){
        uri = "http://localhost:" + randomServerPort + "/api/";
    }
    
    @Test
    @DisplayName("Test if the server is running")
    public void testServerIsRunning() {
        when().
            get(uri + "connections").
        then().
            statusCode(200);
    }

    @Test
    @DisplayName("Get all connections")
    public void testGetAllConnections() {
        when().
            get(uri + "connections").
        then().
            statusCode(200).
            body("size()", greaterThan(0));
    }

}
