package apitests;

import helper.CourierGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Courier;
import model.CourierCreds;
import org.junit.Test;

import static config.Endpoints.COURIER_LOGIN_PATH;
import static config.TestData.*;
import static helper.CourierGenerator.randomCourier;
import static helper.CourierHelper.*;
import static helper.Helper.sendPostRequest;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static model.CourierCreds.from;

public class CourierLoginTest extends BaseTest {
    private CourierCreds cred;

    @Test
    @DisplayName("Успешная авторизация курьера")
    public void courierLoginSuccessful() {
        Courier courier = randomCourier();
        createCourier(courier);
        cred = from(courier);
        Response response = sendPostRequest(cred, COURIER_LOGIN_PATH);
        checkCourierIsLogged(response);

        deleteCourier(courier);
    }

    @Test
    @DisplayName("Ошибка при попытке авторизации без логина")
    public void courierLoginMissed() {
        cred = new CourierCreds();
        cred.setPassword(PASSWORD);
        Response response = sendPostRequest(cred, COURIER_LOGIN_PATH);
        checkResponseValidationError(response, HTTP_BAD_REQUEST, HAS_NOT_ENOUGH_DATA_TO_LOGIN);
    }


    @Test
    @DisplayName("Ошибка при попытке авторизации без пароля")
    @Description("Баг, падает по таймауту (504 Gateway time out)")
    public void courierPasswordMissed() {
        cred = new CourierCreds();
        cred.setLogin(LOGIN);
        Response response = sendPostRequest(cred, COURIER_LOGIN_PATH);
        checkResponseValidationError(response, HTTP_BAD_REQUEST, HAS_NOT_ENOUGH_DATA_TO_LOGIN);
    }

    @Test
    @DisplayName("Ошибка при попытке авторизации c несуществующем в системе пользователем")
    public void courierLoginNonExistLogin() {
        cred = new CourierCreds();
        cred.setLogin(LOGIN + CourierGenerator.randomInt);
        cred.setPassword(PASSWORD);
        Response response = sendPostRequest(cred, COURIER_LOGIN_PATH);
        checkResponseValidationError(response, HTTP_NOT_FOUND, USER_NOT_EXIST);
    }

    @Test
    @DisplayName("Ошибка при попытке авторизации c некорректным паролем")
    public void courierLoginUncorrectedPassword() {
        cred = new CourierCreds();
        cred.setLogin(LOGIN);
        cred.setPassword(PASSWORD + CourierGenerator.randomInt);
        Response response = sendPostRequest(cred, COURIER_LOGIN_PATH);
        checkResponseValidationError(response, HTTP_NOT_FOUND, USER_NOT_EXIST);
    }
    @Test
    @DisplayName("Ошибка при попытке авторизации c логином и паролем = \"\"")
    public void courierLoginEmptyStrings() {
        cred = new CourierCreds();
        cred.setLogin("");
        cred.setPassword("");
        Response response = sendPostRequest(cred, COURIER_LOGIN_PATH);
        checkResponseValidationError(response, HTTP_BAD_REQUEST, HAS_NOT_ENOUGH_DATA_TO_LOGIN);
    }

    @Test
    @DisplayName("Ошибка при попытке авторизации с пустым телом запроса")
    @Description("Баг, падает по таймауту (504 Gateway time out)")
    public void courierLoginEmptyRequest() {
        String emptyJson = "{}";
        Response response = sendPostRequest(emptyJson, COURIER_LOGIN_PATH);
        checkResponseValidationError(response, HTTP_BAD_REQUEST, HAS_NOT_ENOUGH_DATA_TO_LOGIN);
    }

}
