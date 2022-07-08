import io.qameta.allure.Step;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;


public class OrdersApiClient extends BaseHttpClient {
    private static final String OrderApi = "/orders";

    @Step("Create order")
    public Response makeOrder(Order orderParameters, Authorization authorizationInfo) {
        return given()
                .spec(getRequestSpecification())
                .auth().oauth2(authorizationInfo.getAccessToken())
                .body(orderParameters.toJson())
                .when()
                .post(OrderApi);
    }

    @Step("Create order without auth")
    public Response makeOrderWithoutAuth(Order orderParameters) {
        return given()
                .spec(getRequestSpecification())
                .body(orderParameters.toJson())
                .when()
                .post(OrderApi);
    }

    @Step("Accept Order")
    public Response getOrders(Authorization authorizationInfo) {
        return given()
                .spec(getRequestSpecification())
                .auth().oauth2(authorizationInfo.getAccessToken())
                .when()
                .get(OrderApi);
    }
}
