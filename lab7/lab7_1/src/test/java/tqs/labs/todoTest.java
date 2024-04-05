package tqs.labs;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

public class todoTest{
    
    private String uri = "https://jsonplaceholder.typicode.com/";

    @Test
    void testOk() {
        given()
        .when()
            .get(uri+"todos")
        .then()
            .statusCode(200);
    }

    @Test
    void testId4(){
        given()
        .when()
            .get(uri+"todos/4")
        .then()
            .statusCode(200)
            .body("title", 
                equalTo("et porro tempora"));
    }

    @Test
    void testContains198and199(){
        given()
        .when()
            .get(uri+"todos")
        .then()
            .statusCode(200)
            .body("id", 
                hasItems(198,199));
    }

    @Test
    void testCheckLatency(){
        given()
        .when()
            .get(uri+"todos")
        .then()
            .statusCode(200)
            .time(lessThan(2000l));
    }

}