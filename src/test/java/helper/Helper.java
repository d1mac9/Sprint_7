package helper;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Helper {
    public static Response sendPostRequest(Object body, String path) {
        return given()
                .body(body)
                .when()
                .post(path);
    }

    public static Response sendGetRequest(String path) {
        return given()
                .when()
                .get(path);
    }
    public static Response sendGetRequest(String path, int trackId) {
        return given()
                .when()
                .get(path, trackId);
    }

    public static Response sendDeleteRequest(String path, int id) {
        return given()
                .when()
                .delete(path, id);
    }

    public static Response sendDeleteRequest(String path) {
        return given()
                .when()
                .delete(path);
    }

    public static Response sendPutRequest(String path, int trackId, int courierId) {
        return given()
                .basePath(path + "{trackId}")
                .pathParam("trackId", String.valueOf(trackId))
                .queryParam("courierId", String.valueOf(courierId))
                .when()
                .put();
    }
    public static Response sendPutRequest(Object body, String path) {
        return given()
                .body(body)
                .when()
                .put(path);
    }
    public static Response sendPutRequest(String path) {
        return given()
                .when()
                .put(path);
    }
}
