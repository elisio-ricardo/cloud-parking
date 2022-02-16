package one.digitalinnovation.cloudparking.controller;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingControllerTest {

    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest(){
        RestAssured.port = randomPort;
    }


    @Test
    void whenFindAllThenCheckResult() {
//        RestAssured.given()
//                .when()
//                .get("/parking")
//                .then()
//                .extract().response()
//                .body().prettyPrint();
        RestAssured.given()
                .when()
                .get("/parking")
                .then()
                .body("license[0]", Matchers.equalTo("DeMS-1111"));


    }

    @Test
    void create() {

    }
}