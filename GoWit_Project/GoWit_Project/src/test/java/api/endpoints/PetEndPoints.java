package api.endpoints;

import api.payload.Pet;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

//Created for perform Create, Read, Update, Delete requests the Pet API.
public class PetEndPoints {
    public static Response createPet(Pet payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(Routes.post_url);

        return response;
    }

    public static Response readPet(int petId) {
        Response response = given()
                .pathParam("petId", petId)
                .when()
                .get(Routes.get_url);

        return response;
    }

    public static Response updatePet(Pet payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .put(Routes.update_url);

        return response;
    }

    public static Response deletePet(int petId) {
        Response response = given()
                .pathParam("petId", petId)
                .when()
                .delete(Routes.delete_url);

        return response;
    }
}
