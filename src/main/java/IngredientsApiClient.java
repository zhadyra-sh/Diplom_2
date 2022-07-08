import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * API для работы с ингредиентами.
 */
public class IngredientsApiClient extends BaseHttpClient {

    @Step("Get ingredients")
    public Response getIngredients() {
        return given()
                .spec(getRequestSpecification())
                .when()
                .get("ingredients");
    }
}
