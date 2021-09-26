import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class TestClient {

    String urlAPI = "http://localhost:8080";
    String endpointClient = "/cliente";
    String endpointDeleteAll = "/apagaTodos";

    @Test
    @DisplayName("When getting all clients without registering, then the list must be empty.")
    public void getAllClients() {

        deleteAllClientes();

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
    @DisplayName("When updating a client, then the client must be updated.")
    public void putClient() {

        String newClient = "{\n" +
                "  \"id\": 1001,\n" +
                "  \"idade\": 30,\n" +
                "  \"nome\": \"Igor\",\n" +
                "  \"risco\": 50\n" +
                "}";

        String updatedClient = "{\n" +
                "  \"id\": 1001,\n" +
                "  \"idade\": 35,\n" +
                "  \"nome\": \"Igor\",\n" +
                "  \"risco\": 50\n" +
                "}";

        String responseExpected = "{\"1001\":{\"nome\":\"Igor\",\"idade\":35,\"id\":1001,\"risco\":50}}";

        given()
                .contentType(ContentType.JSON)
                .body(newClient)
        .when()
                .post(urlAPI + endpointClient)
        .then()
                .statusCode(201);

        given()
                .contentType(ContentType.JSON)
                .body(updatedClient)
        .when()
                .put(urlAPI + endpointClient)
        .then()
                .statusCode(200)
                .assertThat().body(containsString(responseExpected));
    }

    @Test
    @DisplayName("When deleting a client, then the client must be removed with successful")
    public void deleteCliente() {

        String registerClient = "{\n" +
                "  \"id\": 1001,\n" +
                "  \"idade\": 30,\n" +
                "  \"nome\": \"Igor\",\n" +
                "  \"risco\": 10000\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(registerClient)
        .when()
                .post(urlAPI + endpointClient)
        .then()
                .statusCode(201);

        int idClient = 1001;
        String expectedResult = "CLIENTE REMOVIDO: { NOME: Igor, IDADE: 30, ID: 1001 }";

        given()
                .contentType(ContentType.JSON)
        .when()
                .delete(urlAPI + endpointClient + "/" + idClient)
        .then()
                .statusCode(200)
                .body(new IsEqual<>(expectedResult));
    }

    public void deleteAllClientes() {

        String expectedResult = "{}";

        given()
                .contentType(ContentType.JSON)
        .when()
                .delete(urlAPI + endpointClient + endpointDeleteAll)
        .then()
                .statusCode(200)
                .body(new IsEqual<>(expectedResult));

    }
}
