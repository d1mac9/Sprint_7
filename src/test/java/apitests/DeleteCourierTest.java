package apitests;

import helper.CourierGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Courier;
import org.junit.Test;

import static config.Endpoints.COURIER_ID_PATH;
import static config.Endpoints.COURIER_PATH;
import static config.TestData.*;
import static helper.CourierGenerator.randomCourier;
import static helper.CourierHelper.*;
import static helper.CourierHelper.checkResponseValidationError;
import static helper.Helper.sendDeleteRequest;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

public class DeleteCourierTest extends BaseTest {

    @Test
    @DisplayName("Успешное удаление курьера")
    public void deleteCourierHappyPath() {
        Courier courier = randomCourier();
        createCourier(courier);
        deleteCourier(courier);
    }

    @Test
    @DisplayName("Ошибка при удалении несуществующего курьера")
    public void deleteNonExistCourier() {
        Response response = sendDeleteRequest(COURIER_ID_PATH, CourierGenerator.randomInt);
        checkResponseValidationError(response, HTTP_NOT_FOUND, NO_COURIER_WITH_CURRENT_ID);
    }

    @Test
    @DisplayName("Ошибка при попытке удалить курьера без передачи id курьера")
    public void deleteRequestWithoutId(){
        Response response = sendDeleteRequest(COURIER_PATH);
        checkResponseValidationError(response, HTTP_NOT_FOUND, NOT_FOUND);
    }

}
