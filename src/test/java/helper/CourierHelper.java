package helper;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import model.ValidationErrorResponse;

import static config.Endpoints.*;
import static helper.Helper.sendDeleteRequest;
import static helper.Helper.sendPostRequest;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierHelper {

    public static void checkCourierIsCreated(Response response) {
        response
                .then().assertThat().statusCode(HTTP_CREATED)
                .and()
                .body("ok", equalTo(true));
    }

    public static void createCourier(Object body) {
        checkCourierIsCreated(sendPostRequest(body, COURIER_PATH));
    }

    public  static ValidatableResponse checkCourierIsLogged(Response response){
        return response
                .then().assertThat().statusCode(HTTP_OK)
                .and()
                .body("id", notNullValue());
    }
    public static int getCourierId(Object courier) {
        Response response = sendPostRequest(courier, COURIER_LOGIN_PATH);
        return checkCourierIsLogged(response)
                .extract().path("id");
    }

    public static void deleteCourierById(Integer id) {
        Response response = sendDeleteRequest(COURIER_ID_PATH, id);
        checkCourierIsDeleted(response);
    }
    public static void checkCourierIsDeleted(Response response){
        response
                .then().assertThat().statusCode(HTTP_OK)
                .and()
                .body("ok", equalTo(true));
    }

    public static void deleteCourier(Object courier) {
        deleteCourierById(getCourierId(courier));
    }

    public static void checkResponseValidationError(Response response, int statusCode, String text) {
        ValidationErrorResponse validationErrorResponse = response
                .then().assertThat().statusCode(statusCode)
                .and()
                .extract()
                .as(ValidationErrorResponse.class);
        assertEquals(statusCode, validationErrorResponse.getCode());
        assertEquals(text, validationErrorResponse.getMessage());
    }

}
