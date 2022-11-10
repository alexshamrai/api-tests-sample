package in.reqres;

import com.github.javafaker.Faker;
import in.reqres.controller.ReqResController;
import in.reqres.model.UserDto;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static io.restassured.RestAssured.responseSpecification;

public class CreateUserTest {

    static {
        requestSpecification = new RequestSpecBuilder().log(LogDetail.ALL).build();
        responseSpecification = new ResponseSpecBuilder().log(LogDetail.ALL).build();
    }

    Faker faker = new Faker();
    private final String password = faker.music().instrument();
    private final String EMAIL = "eve.holt@reqres.in";

    private final ReqResController reqResController = new ReqResController();

    @Test
    @DisplayName("Creation of a new user")
    void creationOfANewUser() {
        var targetUser = UserDto.builder()
                                .email(EMAIL)
                                .password(password)
                                .build();
        var createUserResponse = reqResController.createNewUser(targetUser);
        createUserResponse.prettyPrint();

        Assertions.assertEquals(200, createUserResponse.statusCode());

        var userId = createUserResponse.as(UserDto.class).getId();
        var getByIdResponse = reqResController.getUserById(userId);
        getByIdResponse.prettyPrint();

        Assertions.assertEquals(200, getByIdResponse.statusCode());
        Assertions.assertTrue(getByIdResponse.jsonPath().get("data").toString().contains(EMAIL));
    }
}
