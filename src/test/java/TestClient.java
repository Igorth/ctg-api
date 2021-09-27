import io.restassured.http.ContentType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

import org.hamcrest.core.IsEqual;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class TestClient {

    private String urlAPI = "http://localhost:8080";
    private String endpointClient = "/cliente";
    private String endpointDeleteAll = "/apagaTodos";
    private static final String expectedResult = "{}";

    @Test
    @DisplayName("When getting all clients without registering, then the list must be empty.")
    public void whenGettingAllClientsWithoutRegistering_ThenTheListMustBeEmpty() {

        deleteAllClientes();

        given()
                .contentType(ContentType.JSON)
        .when()
                .get(urlAPI)
        .then()
                .statusCode(200)
                .body(equalTo(expectedResult));

    }

    @Test
    @DisplayName("When registering a client, then it is must be available in the result.")
    public void whenRegisteringAClient_ThenItIsMustBeAvailableInTheResult() {

        Client registerClient = new Client();

        registerClient.setNome("Igor");
        registerClient.setIdade(30);
        registerClient.setId(1001);
        registerClient.setRisco(10);

        given()
                .contentType(ContentType.JSON)
                .body(registerClient)
        .when()
                .post(urlAPI + endpointClient)
        .then()
                .statusCode(201)
                .body("1001.nome", equalTo("Igor"))
                .body("1001.idade", equalTo(30))
                .body("1001.risco", equalTo(10))
                .body("1001.id", equalTo(1001));
    }

    @Test
    @DisplayName("When updating a client, then the client must be updated.")
    public void whenUpdatingAClient_ThenTheClientMustBeUpdated() {

        Client registerClient = new Client();

        registerClient.setNome("Igor");
        registerClient.setIdade(30);
        registerClient.setId(1001);
        registerClient.setRisco(10);

        Client updatedClient = new Client();

        updatedClient.setNome("Laisa");
        updatedClient.setIdade(33);
        updatedClient.setId(1001);
        updatedClient.setRisco(10);


        given()
                .contentType(ContentType.JSON)
                .body(registerClient)
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
                .body("1001.nome", equalTo("Laisa"))
                .body("1001.idade", equalTo(33))
                .body("1001.id", equalTo(1001))
                .body("1001.risco", equalTo(10));
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
