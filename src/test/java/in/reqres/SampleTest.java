package in.reqres;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SampleTest {

    @Test
    @DisplayName("Just a sample")
    void sampleTest() {
        RestAssured.given()
            .baseUri("https://reqres.in")
            .basePath("/api/users/")
            .contentType(ContentType.JSON)
            .when()
            .get("1")
            .then()
            .statusCode(200);
    }
}
