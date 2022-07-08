import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public class BaseHttpClient {

    public final String HEADER_CONTENT_TYPE = "application/json";

    public static final String API_URL = "https://stellarburgers.nomoreparties.site/api";

    public static RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .setBaseUri(API_URL)
                .build();
    }
}
