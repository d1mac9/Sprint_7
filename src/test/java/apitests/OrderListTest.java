package apitests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static config.Endpoints.ORDERS_PATH;
import static helper.Helper.sendGetRequest;
import static helper.OrderHelper.checkOrdersResponseIsNotNull;

public class OrderListTest extends BaseTest {
    @Test
    @DisplayName("Успешное получение списка ордеров")
    public void getOrderListHappyPath(){
        Response response = sendGetRequest(ORDERS_PATH);
        checkOrdersResponseIsNotNull(response);
    }
}
