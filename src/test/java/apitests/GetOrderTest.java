package apitests;

import helper.CourierGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Order;
import org.junit.After;
import org.junit.Test;

import static config.Endpoints.ORDER_TRACK_NULL;
import static config.TestData.*;
import static helper.CourierHelper.checkResponseValidationError;
import static helper.Helper.sendGetRequest;
import static helper.OrderGenerator.genericOrder;
import static helper.OrderHelper.*;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

public class GetOrderTest extends BaseTest {
    private int trackId;

    @Test
    @DisplayName("Успешное получение ордера")
    public void getOrderByTrackHappyPath() {
        Order order = genericOrder();
        trackId = createOrder(order);

        checkOrderResponse(trackId, order);
    }

    @Test
    @DisplayName("Ошибка при попытке получения ордера без передачи order id")
    public void getOrderNonExistTrack() {
        Response response = getOrder(CourierGenerator.randomInt);
        checkResponseValidationError(response, HTTP_NOT_FOUND, ORDER_NOT_FOUND);
    }

    @Test
    @DisplayName("Ошибка при попытке получения ордера без передачи track id")
    public void getOrderWithoutTrack() {
        Response response = sendGetRequest(ORDER_TRACK_NULL);
        checkResponseValidationError(response, HTTP_BAD_REQUEST, HAS_NOT_ENOUGH_DATA_TO_SEARCH);
    }
    @After
    public void cancelOrder() {
        if (trackId > 0) {
            cancelOrderByTrackId(trackId);
        }
    }
}
