import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class TestClient {

    String urlAPI = "http://localhost:8080";
    String endpointClient = "cliente";

    @Test
    @DisplayName("When getting all clients without registering, then the list must be empty.")
    public void getAllClients() {

        String expectedResult = "{}";

        given()
                .contentType(ContentType.JSON)
        .when()
                .get(urlAPI)
        .then()
                .statusCode(200)
                .assertThat().body(new IsEqual<>(expectedResult));

    }
}
