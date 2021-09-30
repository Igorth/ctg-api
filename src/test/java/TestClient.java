import io.restassured.http.ContentType;

import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.hamcrest.core.IsEqual;

public class TestClient {

    private static String urlClient = "http://localhost:8080";
    private static String endpointClient = "/cliente";
    private static String deleteAllClients = "/apagaTodos";
    private static final String clientEmptyList = "{}";

    @Test
    @DisplayName("When getting all clients without registering, then the list must be empty.")
    public void whenGettingAllClientsWithoutRegistering_ThenTheListMustBeEmpty() {

        deleteAllClients();

        getAllClients()
                .statusCode(200)
                .body(equalTo(clientEmptyList));
    }

    @Test
    @DisplayName("When registering a client, then it is must be available in the result.")
    public void whenRegisteringAClient_ThenItIsMustBeAvailableInTheResult() {

        Client registerClient = new Client("Igor",30,1001,10);

        postClient(registerClient)
                .statusCode(HttpStatus.SC_CREATED)
                .body("1001.nome", equalTo("Igor"))
                .body("1001.idade", equalTo(30))
                .body("1001.risco", equalTo(10))
                .body("1001.id", equalTo(1001));
    }

    @Test
    @DisplayName("When updating a client, then the client must be updated.")
    public void whenUpdatingAClient_ThenTheClientMustBeUpdated() {

        Client registerClient = new Client("Igor",30,1001,10);

        Client updatedClient = new Client("Laisa", 33, 1001, 10);


        postClient(registerClient)
                .statusCode(HttpStatus.SC_CREATED);

        putClient(updatedClient)
                .statusCode(HttpStatus.SC_OK)
                .body("1001.nome", equalTo("Laisa"))
                .body("1001.idade", equalTo(33))
                .body("1001.id", equalTo(1001))
                .body("1001.risco", equalTo(10));
    }

    @Test
    @DisplayName("When deleting a client, then the client must be removed with successful")
    public void whenDeletingAClient_ThenTheClientMustBeRemovedWithSuccessful() {

        Client client = new Client("Igor",30,1001,10);

        postClient(client)
                .statusCode(HttpStatus.SC_CREATED);

        deleteClient(client)
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body(not(contains("Igor")));
    }

    /**
     * Deletes a specific client from our test API
     * @param deleteThisClient
     */
    private ValidatableResponse deleteClient(Client deleteThisClient) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .delete(urlClient + endpointClient + "/" + deleteThisClient.getId())
                .then();
    }

    /**
     * Update client to our test API
     * @param clientToPut
     */
    private ValidatableResponse putClient(Client clientToPut) {
        return given()
                .contentType(ContentType.JSON)
                .body(clientToPut)
                .when()
                .put(urlClient + endpointClient)
                .then();
    }

    /**
     * Post client to our test API
     * @param clientToPost
     */
    private ValidatableResponse postClient(Client clientToPost){
        return given()
                .contentType(ContentType.JSON)
                .body(clientToPost)
                .when()
                .post(urlClient + endpointClient)
                .then();
    }

    /**
     * Get all customers registered in the API
     * @return lists all clients wrapped in restAssured's response type
     */
    private ValidatableResponse getAllClients() {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get(urlClient)
                .then();
    }

    /**
     * Support method to delete all clients from server.
     * Used for testing only.
     * Including a hook to run at the end of each test and leave the server in the same state it was before.
     * Explicitly called in some tests as well as preparation
     */
    @AfterEach
    private void deleteAllClients() {

        String expectedResult = "{}";

        given()
                .contentType(ContentType.JSON)
        .when()
                .delete(urlClient + endpointClient + deleteAllClients)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body(new IsEqual<>(expectedResult));

    }
}