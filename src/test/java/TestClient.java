import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class TestClient {

    String urlAPI = "http://localhost:8080";
    String endpointClient = "/cliente";

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

    @Test
    @DisplayName("When registering a client, then it must be available in the result.")
    public void postClient() {

        String registerClient = "{\n" +
                "  \"id\": 1001,\n" +
                "  \"idade\": 30,\n" +
                "  \"nome\": \"Igor\",\n" +
                "  \"risco\": 10000\n" +
                "}";

        String expectedResult = "{\"1001\":{\"nome\":\"Igor\",\"idade\":30,\"id\":1001,\"risco\":10000}}";

        given()
                .contentType(ContentType.JSON)
                .body(registerClient)
        .when()
                .post(urlAPI + endpointClient)
        .then()
                .statusCode(201)
                .assertThat().body(containsString(expectedResult));

    }

    @Test
    @DisplayName("When updating a client, then it must be available in the result")
    public void putClient() {

        String updatedClient = "{\n" +
                "  \"id\": 1001,\n" +
                "  \"idade\": 35,\n" +
                "  \"nome\": \"Igory\",\n" +
                "  \"risco\": 50\n" +
                "}";

        String expectedResult = "{\"1001\":{\"nome\":\"Igory\",\"idade\":35,\"id\":1001,\"risco\":50}}";

        given()
                .contentType(ContentType.JSON)
                .body(updatedClient)
        .when()
                .put(urlAPI + endpointClient)
        .then()
                .statusCode(200)
                .body(containsString(expectedResult));

    }
}
