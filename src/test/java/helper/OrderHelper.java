package helper;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import model.Order;

import static config.Endpoints.*;
import static helper.Helper.sendGetRequest;
import static helper.Helper.sendPostRequest;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderHelper {

    public static ValidatableResponse checkOrderIsCreated(Response response) {
        return response
                .then().assertThat().statusCode(HTTP_CREATED)
                .and()
                .body("track", notNullValue());
    }

    public static int getTrackId(Response response) {
        return checkOrderIsCreated(response)
                .extract()
                .path("track");
    }

    public static int createOrder(Object orderBody) {
        Response response = sendPostRequest(orderBody, ORDERS_PATH);
        return getTrackId(response);
    }

    public static void checkOrdersResponseIsNotNull(Response response) {
        response
                .then().assertThat().statusCode(HTTP_OK)
                .and()
                .body("orders", notNullValue());
    }

    public static void checkOrderIsAccepted(Response response) {
        response
                .then().assertThat().statusCode(HTTP_OK)
                .and()
                .body("ok", equalTo(true));
    }

    public static void cancelOrderByTrackId(int trackId) {
        Response response = given()
                .queryParam("track", trackId)
                .put(ORDERS_CANCEL);
        checkOrderIsCancelled(response);
    }

    public static void checkOrderIsCancelled(Response response) {
        response
                .then().assertThat().statusCode(HTTP_OK)
                .and()
                .body("ok", equalTo(true));
    }

    public static int getOrderIdByTrackId(int trackId) {
        return getOrder(trackId)
                .then().assertThat().statusCode(HTTP_OK)
                .and()
                .body("order.id", notNullValue())
                .extract()
                .path("order.id");
    }

    public static Response getOrder(int trackId) {
        return sendGetRequest(ORDERS_TRACK, trackId);
    }

    public static void checkOrderResponse(int trackId, Order order) {
        getOrder(trackId)
                .then().statusCode(200)
                .and()
                .body("order.firstName", equalTo(order.getFirstName()))
                .body("order.lastName", equalTo(order.getLastName()))
                .body("order.address", equalTo(order.getAddress()))
                .body("order.metroStation", equalTo(String.valueOf(order.getMetroStation())))
                .body("order.phone", equalTo(order.getPhone()))
                .body("order.rentTime", equalTo(order.getRentTime()))
                .body("order.deliveryDate", equalTo(order.getDeliveryDate()+"T00:00:00.000Z"))
                .body("order.comment", equalTo(order.getComment()))
                .body("order.color", equalTo(order.getColor()));

    }

    public static Response acceptOrder(int orderId, int courierId) {
        return given()
                .pathParam("orderId", String.valueOf(orderId))
                .queryParam("courierId", courierId)
                .when()
                .put(ORDERS_ACCEPT + "{orderId}");
    }
}
