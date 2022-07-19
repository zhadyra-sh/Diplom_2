package client;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Authorization;
import models.Order;

import static io.restassured.RestAssured.given;

public class OrdersApiClient extends BaseHttpClient {
    private static final String ORDER_API = "/orders";

    @Step("Create order")
    public Response makeOrder(Order orderParameters, Authorization authorizationInfo) {
        return given()
                .spec(getRequestSpecification())
                .auth().oauth2(authorizationInfo.getAccessToken())
                .body(orderParameters.toJson())
                .when()
                .post(ORDER_API);
    }

    @Step("Create order without auth")
    public Response makeOrderWithoutAuth(Order orderParameters) {
        return given()
                .spec(getRequestSpecification())
                .body(orderParameters.toJson())
                .when()
                .post(ORDER_API);
    }

    @Step("Accept model.Order")
    public Response getOrders(Authorization authorizationInfo) {
        return given()
                .spec(getRequestSpecification())
                .auth().oauth2(authorizationInfo.getAccessToken())
                .when()
                .get(ORDER_API);
    }
}
