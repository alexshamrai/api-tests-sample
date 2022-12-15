package in.reqres;


import com.github.javafaker.Faker;
import in.reqres.controller.ReqResController;
import in.reqres.model.LoginDto;
import in.reqres.model.UserDto;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.responseSpecification;
import static io.restassured.RestAssured.requestSpecification;

public class LoginUserTest {

    static {
        requestSpecification = new RequestSpecBuilder().log(LogDetail.ALL).build();
        responseSpecification = new ResponseSpecBuilder().log(LogDetail.ALL).build();
    }

    Faker faker = new Faker();
    private final String password = faker.rockBand().name();
    private final String EMAIL = "eve.holt@reqres.in";

    private final ReqResController reqResController = new ReqResController();

    @Test
    @DisplayName("User login")
    void loginUser() {
        var loginUser = LoginDto.builder()
                                .email(EMAIL)
                                .password(password)
                                .build();
        var loginUserResponse = reqResController.login(loginUser);
        assertThat(loginUserResponse.statusCode()).isEqualTo(201);
        var token = loginUserResponse.as(UserDto.class);
        assertThat(token.getId()).isNotNull();
    }
}
