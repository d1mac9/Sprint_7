package apitests;

import helper.CourierGenerator;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Courier;
import model.Order;
import org.junit.After;
import org.junit.Test;

import static config.Endpoints.ORDERS_ACCEPT;
import static config.TestData.*;
import static helper.CourierGenerator.randomCourier;
import static helper.CourierHelper.*;
import static helper.Helper.sendPutRequest;
import static helper.OrderGenerator.genericOrder;
import static helper.OrderHelper.*;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

public class AcceptOrderTest extends BaseTest {
    private final Order order = genericOrder();
    private int courierId;

    @Test
    @DisplayName("Успешное принятие заказа курьером")
    public void acceptOrderHappyPath() {
        //Pre-con
        Courier courier = randomCourier();
        createCourier(courier);
        courierId = getCourierId(courier);
        int trackId = createOrder(order);
        int orderId = getOrderIdByTrackId(trackId);

        Response response = acceptOrder(orderId, courierId);
        checkOrderIsAccepted(response);

    }

    @Test
    @DisplayName("Ошибка, при попытке принять ордер без id курьера")
    public void acceptOrderWithoutCourierId() {
        Courier courier = randomCourier();
        createCourier(courier);
        int trackId = createOrder(order);
        int orderId = getOrderIdByTrackId(trackId);

        Response response = sendPutRequest(ORDERS_ACCEPT + orderId + "?courierId=");
        checkResponseValidationError(response, HTTP_BAD_REQUEST, HAS_NOT_ENOUGH_DATA_TO_SEARCH);
    }

    @Test
    @DisplayName("Ошибка, при попытке принять ордер с некорректным id курьера")
    public void acceptOrderUncorrectedCourierId() {
        int uncorrectedCourierId = CourierGenerator.randomInt;
        int trackId = createOrder(order);
        int orderId = getOrderIdByTrackId(trackId);
        Response response = acceptOrder(orderId, uncorrectedCourierId);
        checkResponseValidationError(response, HTTP_NOT_FOUND, COURIER_WITH_CURRENT_ID_NOT_EXIST);

        cancelOrderByTrackId(trackId);
    }

    @Test
    @DisplayName("Ошибка, при попытке принять ордер без id заказа")
    public void acceptOrderWithoutOrderId(){
        Courier courier = randomCourier();
        createCourier(courier);
        courierId = getCourierId(courier);

        Response response = sendPutRequest(ORDERS_ACCEPT + "?courierId=" + courierId);
        checkResponseValidationError(response, HTTP_NOT_FOUND, NOT_FOUND);

    }
    @Test
    @DisplayName("Ошибка, при попытке принять ордер с некорректным id заказа")
    public void acceptOrderUncorrectedOrderId(){
        Courier courier = randomCourier();
        createCourier(courier);
        courierId = getCourierId(courier);
        int uncorrectedOrderId = CourierGenerator.randomInt;

        Response response = acceptOrder(uncorrectedOrderId, courierId);
        checkResponseValidationError(response, HTTP_NOT_FOUND, ORDER_WITH_CURRENT_ID_NOT_EXIST);

    }
    @After
    @Step("Удаление курьера")
    public void deleteCourier() {
        if (courierId > 0) {
            deleteCourierById(courierId);
        }
    }

}
