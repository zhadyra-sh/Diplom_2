
import client.UsersApiClient;
import io.qameta.allure.junit4.DisplayName;

import models.Authorization;
import models.User;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Тестирование изменения информации о пользователе.
 */
public class UserInfoChangingTest {
    private final UsersApiClient usersApiClient = new UsersApiClient();

    @After
    public void afterTest() {
        usersApiClient.deleteCreatedUsers();
    }

    User user;
    Authorization authorizationInfo;

    @Before
    public void beforeTest() {
        user = User.getRandomUser();
        authorizationInfo = usersApiClient.register(user).as(Authorization.class);
    }

    @Test
    @DisplayName("It should be possible to change the user's Email with authorization.")
    public void shouldChangeEmailAuthUserTest() {
        user.setEmail(User.getRandomEmail());
        authorizationInfo.setUser(user);
        usersApiClient
                .patchAuthUserInfo(authorizationInfo)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("It should be possible to change the username with authorization.")
    public void shouldChangeAuthUsersNameTest() {
        user.setName(User.getRandomName());
        authorizationInfo.setUser(user);
        usersApiClient
                .patchAuthUserInfo(authorizationInfo)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("It should be possible to change the user's password with authorization.")
    public void shouldChangeAuthUsersPasswordTest() {
        user.setPassword(User.getRandomPassword());
        authorizationInfo.setUser(user);
        usersApiClient
                .patchAuthUserInfo(authorizationInfo)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("It should not be possible to change the user's Email without authorization.")
    public void shouldNotChangeUsersEmailWithoutAuthTest() {
        user.setEmail(User.getRandomEmail());
        usersApiClient
                .patchNotAuthUserInfo(user)
                .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().assertThat().body("success", equalTo(false));
    }

    @Test
    @DisplayName("It should not be possible to change the username without authorization.")
    public void shouldNotChangeUsersNameWithoutAuthTest() {
        user.setName(User.getRandomName());
        usersApiClient
                .patchNotAuthUserInfo(user)
                .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().assertThat().body("success", equalTo(false));
    }

    @Test
    @DisplayName("It should not be possible to change a user's password without authorization.")
    public void shouldNotChangeUsersPasswordWithoutAuthTest() {
        user.setPassword(User.getRandomPassword());
        usersApiClient
                .patchNotAuthUserInfo(user)
                .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().assertThat().body("success", equalTo(false));
    }
}