package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Authorization;
import models.User;
import org.apache.http.HttpStatus;
import java.util.ArrayList;
import static io.restassured.RestAssured.given;
public class UsersApiClient extends BaseHttpClient {

    private final ArrayList<User> createdUsers = new ArrayList<>();

    @Step("New user register")
    public Response register(User user) {
        createdUsers.add(user);

        return given()
                .spec(getRequestSpecification())
                .body(user.toJson())
                .when()
                .post("auth/register");
    }

    @Step("Get user")
    public Response getUser(Authorization authorizationInfo) {
        return given()
                .spec(getRequestSpecification())
                .auth().oauth2(authorizationInfo.getAccessToken())
                .when()
                .get("auth/user");
    }

    @Step("Update user")
    public Response patchAuthUserInfo(Authorization authorizationInfo) {
        return given()
                .spec(getRequestSpecification())
                .auth().oauth2(authorizationInfo.getAccessToken())
                .body(authorizationInfo.getUser().toJson())
                .when()
                .patch("auth/user");
    }

    @Step("Update info user")
    public Response patchNotAuthUserInfo(User user) {
        return given()
                .spec(getRequestSpecification())
                .body(user.toJson())
                .when()
                .patch("auth/user");
    }

    @Step("Auth user")
    public Response login(User user) {
        return given()
                .spec(getRequestSpecification())
                .body(user.toJson())
                .when()
                .post("auth/login");
    }

    @Step("Remove user")
    public void deleteUser(User user) {
        Response response = login(user);
        if (response.statusCode() == HttpStatus.SC_OK) {
            Authorization authorizationInfo = response.as(Authorization.class);

            given()
                    .spec(getRequestSpecification())
                    .auth().oauth2(authorizationInfo.getAccessToken())
                    .when()
                    .delete("auth/user");
        }
    }

    @Step("Remove all created user")
    public void deleteCreatedUsers() {
        createdUsers.forEach(this::deleteUser);
    }
}
