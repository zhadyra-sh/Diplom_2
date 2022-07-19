
import client.IngredientsApiClient;
import client.OrdersApiClient;
import client.UsersApiClient;
import io.qameta.allure.junit4.DisplayName;

import models.Authorization;
import models.Ingredient;
import models.Order;
import models.User;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Тестирование заказов.
 */
public class OrderTest {

    private final OrdersApiClient ordersApiClient = new OrdersApiClient();
    private final IngredientsApiClient ingredientsApiClient = new IngredientsApiClient();
    private final UsersApiClient usersApiClient = new UsersApiClient();

    @After
    public void afterTest() {
        usersApiClient.deleteCreatedUsers();
    }

    private List<Ingredient> getIngredients() {
        return ingredientsApiClient
            .getIngredients().then().extract().body().jsonPath()
            .getList("data", Ingredient.class);
    }

    private Authorization getRandomUserAuthInfo() {
        return usersApiClient
                .register(User.getRandomUser())
                .as(Authorization.class);
    }

    @Test
    @DisplayName("It should be possible to create an order for a user with authorization.")
    public void shouldMakeOrderTest() {
        List<Ingredient> ingredients = getIngredients();

        if (ingredients.size() == 0) {
            Assert.fail("There are no ingredients to be able to create an order.");
        }

        ArrayList<String> orderIngredients = new ArrayList<>();
        orderIngredients.add(ingredients.get(0).getId());

        Authorization authorizationInfo = getRandomUserAuthInfo();

        ordersApiClient
                .makeOrder(new Order(orderIngredients), authorizationInfo)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("It should be possible to create an order without authorization.")
    public void makeOrderWithoutAuthTest() {
        List<Ingredient> ingredients = getIngredients();

        if (ingredients.size() == 0) {
            Assert.fail("There are no ingredients to be able to create an order.");
        }

        ArrayList<String> orderIngredients = new ArrayList<>();
        orderIngredients.add(ingredients.get(0).getId());

        ordersApiClient
                .makeOrderWithoutAuth(new Order(orderIngredients))
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("There must be an error when ordering without ingredients.")
    public void shouldGetErrorWhenMakeOrderWithoutIngredientsTest() {
        Authorization authorizationInfo = getRandomUserAuthInfo();

        ordersApiClient
                .makeOrder(new Order(new ArrayList<>()), authorizationInfo)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .and().assertThat().body("success", equalTo(false));
    }

    @Test
    @DisplayName("There must be an error when ordering invalid hash ingredients")
    public void shouldGetErrorWhenMakeOrderWithWrongIngredientTest() {
        Authorization authorizationInfo = getRandomUserAuthInfo();

        ArrayList<String> orderIngredients = new ArrayList<>();
        orderIngredients.add("2022a");

        ordersApiClient
                .makeOrder(new Order(orderIngredients), authorizationInfo)
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("It should be possible to get a list of user orders with authorization.")
    public void shouldGetOrdersByUserWithAuthTest() {
        List<Ingredient> ingredients = getIngredients();

        if (ingredients.size() == 0) {
            Assert.fail("There are no ingredients to be able to create an order.");
        }

        ArrayList<String> orderIngredients = new ArrayList<>();
        orderIngredients.add(ingredients.get(0).getId());

        Authorization authorizationInfo = getRandomUserAuthInfo();

        ordersApiClient.makeOrder(new Order(orderIngredients), authorizationInfo);

        ordersApiClient
                .getOrders(authorizationInfo)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().assertThat().body("success", equalTo(true));
    }
}
