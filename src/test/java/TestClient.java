import io.restassured.http.ContentType;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.hamcrest.core.IsEqual;

public class TestClient {

    private String urlAPI = "http://localhost:8080";
    private String endpointClient = "/cliente";
    private String endpointDeleteAll = "/apagaTodos";
    private static final String expectedResult = "{}";

    @Test
    @DisplayName("When getting all clients without registering, then the list must be empty.")
    public void whenGettingAllClientsWithoutRegistering_ThenTheListMustBeEmpty() {

        deleteAllClients();

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
                .statusCode(HttpStatus.SC_CREATED)
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
                .statusCode(HttpStatus.SC_CREATED);

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
    public void whenDeletingAClient_ThenTheClientMustBeRemovedWithSuccessful() {

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
                .statusCode(HttpStatus.SC_CREATED);

        given()
                .contentType(ContentType.JSON)
        .when()
                .delete(urlAPI + endpointClient + "/" + registerClient.getId())
        .then()
                .statusCode(200)
                .assertThat().body(not(contains("Igor")));
    }

    public void deleteAllClients() {

        String expectedResult = "{}";

        given()
                .contentType(ContentType.JSON)
        .when()
                .delete(urlAPI + endpointClient + endpointDeleteAll)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body(new IsEqual<>(expectedResult));

    }
}