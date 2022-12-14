package in.reqres;

import com.github.javafaker.Faker;
import in.reqres.controller.ReqResController;
import in.reqres.model.UserDto;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.requestSpecification;
import static io.restassured.RestAssured.responseSpecification;

public class CreateUserTest {

    static {
        requestSpecification = new RequestSpecBuilder().log(LogDetail.ALL).build();
        responseSpecification = new ResponseSpecBuilder().log(LogDetail.ALL).build();
    }

    Faker faker = new Faker();
    private final String password = faker.rockBand().name();
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
        assertThat(createUserResponse.statusCode()).isEqualTo(200);

        var userId = createUserResponse.as(UserDto.class).getId();
        var actualUser = reqResController.getUserById(userId);

        actualUser.prettyPrint();
        assertThat(actualUser.statusCode()).isEqualTo(200);
        assertThat(actualUser.jsonPath().get("data").toString()).contains(EMAIL);

        var deleteUser = reqResController.deleteUser(userId);
        assertThat(deleteUser.statusCode()).isEqualTo(204);
    }
}
