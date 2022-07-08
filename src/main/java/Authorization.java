/**
 * Информация об авторизации.
 */
public class Authorization {
    public Boolean success;
    public User user;
    public String accessToken;
    public String refreshToken;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccessToken(){
        return accessToken.replace("Bearer ", "");
    }
}
