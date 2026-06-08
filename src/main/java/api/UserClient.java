package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient {
    private static final String REGISTER_ENDPOINT = "/api/auth/register";


    @Step("Регистрация пользователя")
    public Response register(User user) {
        return given().header("Content-type", "Application/json")
                .body(user)
                .when()
                .post(REGISTER_ENDPOINT);
    }

    @Step("Удаление пользователя по email")
    public Response deleteUserByEmail(String email) {
        return given()
                .header("Content-Type", "application/json")
                .queryParam("email", email)
                .when()
                .delete("/api/auth/user");
    }

}