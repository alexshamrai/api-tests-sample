package in.reqres;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CreateUserTest {

    Faker faker = new Faker();
    private final String password = faker.music().instrument();
    private final String EMAIL = "eve.holt@reqres.in";


    @Test
    @DisplayName("Creation of a new user")
    void creationOfANewUser() {
        var creationResponse = reqresClient()
                                   .log().everything()
                                   .body("{\n"
                                         + "    \"email\": \"" + EMAIL + "\",\n"
                                         + "    \"password\": \"" + password + "\"\n"
                                         + "}")
                                   .contentType(ContentType.JSON)
                                   .when()
                                   .post("/register");
        creationResponse.prettyPrint();

        creationResponse.then()
                        .assertThat()
                        .statusCode(200);
        var userId = creationResponse.jsonPath().get("id").toString();


        var getByIdResponse = reqresClient()
            .when()
            .get("/users/{id}", userId);
        getByIdResponse.prettyPrint();
        getByIdResponse.then()
            .assertThat()
            .statusCode(200);

        var abc = getByIdResponse.jsonPath().get("data");
        Assertions.assertTrue(getByIdResponse.jsonPath().get("data").toString().contains(EMAIL));
    }

    private RequestSpecification reqresClient() {
        return given()
                   .baseUri("https://reqres.in")
                   .basePath("/api");
    }
}
