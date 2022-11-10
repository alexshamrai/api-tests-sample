package in.reqres.controller;

import in.reqres.model.UserDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ReqResController {

    public Response createNewUser(UserDto userDto) {
        return reqresApiClient()
            .body(userDto)
            .post("/register");
    }

    public Response getUserById(int userId) {
        return reqresApiClient()
                   .get("/users/{id}", userId);
    }

    private RequestSpecification reqresApiClient() {
        return given()
                   .baseUri("https://reqres.in")
                   .basePath("/api")
                   .contentType(ContentType.JSON);
    }
}
