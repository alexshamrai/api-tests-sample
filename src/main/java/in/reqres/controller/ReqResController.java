package in.reqres.controller;

import in.reqres.model.UserDto;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ReqResController {

    @Step("Create new user")
    public Response createNewUser(UserDto userDto) {
        return reqresApiClient()
            .body(userDto)
            .post("/register");
    }

    @Step("Get user by id")
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
