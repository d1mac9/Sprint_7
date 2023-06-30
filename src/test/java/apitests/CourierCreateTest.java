package apitests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Courier;
import org.junit.After;
import org.junit.Test;

import static config.Endpoints.COURIER_PATH;
import static config.TestData.*;
import static helper.CourierGenerator.randomCourier;
import static helper.CourierHelper.*;
import static helper.Helper.sendPostRequest;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CONFLICT;

public class CourierCreateTest extends BaseTest {
    private Courier courier;

    @Test
    @DisplayName("Успешное создание курьера")
    public void courierCreatePositive() {
        courier = randomCourier();
        Response response = sendPostRequest(courier, COURIER_PATH);
        checkCourierIsCreated(response);
    }

    @Test
    @DisplayName("Ошибка при попытке создания дубликата курьера")
    public void courierCreateDuplicateFail() {
        courier = randomCourier();
        createCourier(courier);

        Response response = sendPostRequest(courier, COURIER_PATH);
        checkResponseValidationError(response, HTTP_CONFLICT, LOGIN_ALREADY_USED);
    }

    @Test
    @DisplayName("Ошибка при попытке создания курьера без логина")
    public void courierCreateLoginMissed() {
        courier = new Courier();
        courier.setPassword(PASSWORD);
        courier.setFirstName(FIRST_NAME);
        Response response = sendPostRequest(courier, COURIER_PATH);
        checkResponseValidationError(response, HTTP_BAD_REQUEST, HAS_NOT_ENOUGH_DATA_TO_CREATE_ACCOUNT);
    }

    @Test
    @DisplayName("Ошибка при попытке создания курьера без пароля")
    public void courierCreatePasswordMissed() {
        courier = new Courier();
        courier.setLogin(LOGIN);
        courier.setFirstName(FIRST_NAME);
        Response response = sendPostRequest(courier, COURIER_PATH);
        checkResponseValidationError(response, HTTP_BAD_REQUEST, HAS_NOT_ENOUGH_DATA_TO_CREATE_ACCOUNT);
    }

    @Test
    @DisplayName("Ошибка при попытке создания курьера без имени")
    @Description("Баг, происзодит создание сущности без обязательного поля firstName, ожидаемый статус код = 400")
    public void courierCreateFirstNameMissed() {
        courier = new Courier();
        courier.setLogin(LOGIN);
        courier.setPassword(PASSWORD);
        Response response = sendPostRequest(courier, COURIER_PATH);
        checkResponseValidationError(response, HTTP_BAD_REQUEST, HAS_NOT_ENOUGH_DATA_TO_CREATE_ACCOUNT);
    }

    @Test
    @DisplayName("Ошибка при попытке создания курьера со значением логина и пароля = \"\"")
    public void courierCreateEmptySting() {
        courier = new Courier("", "", "");
        Response response = sendPostRequest(courier, COURIER_PATH);
        checkResponseValidationError(response, HTTP_BAD_REQUEST, HAS_NOT_ENOUGH_DATA_TO_CREATE_ACCOUNT);
    }

    @Test
    @DisplayName("Ошибка при попытке создания курьера со значением значением логина, пароля и имени = null")
    public void courierCreateNullFields() {
        courier = new Courier();
        Response response = sendPostRequest(courier, COURIER_PATH);
        checkResponseValidationError(response, HTTP_BAD_REQUEST, HAS_NOT_ENOUGH_DATA_TO_CREATE_ACCOUNT);
    }

    @After
    @Step("Удаление курьера")
    public void deleteCourierData(){
            try {
                deleteCourier(courier);
            } catch (AssertionError ignored){}
    }

}
