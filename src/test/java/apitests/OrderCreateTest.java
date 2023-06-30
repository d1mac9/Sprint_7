package apitests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Color;
import model.Order;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static config.Endpoints.ORDERS_PATH;
import static helper.Helper.sendPostRequest;
import static helper.OrderHelper.*;

@RunWith(Parameterized.class)
public class OrderCreateTest extends BaseTest {
    private int trackId;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final Integer metroStation;
    private final String phone;
    private final Integer rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<Color> color;

    public OrderCreateTest(String firstName,
                           String lastName,
                           String address,
                           Integer metroStation,
                           String phone,
                           Integer rentTime,
                           String deliveryDate,
                           String comment,
                           List<Color> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] testData() {
        return new Object[][]{
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", List.of(Color.BLACK)},
                {"Имя", "Фамилия", "какой то адрес дом 14", 1, "+79111660000", 1, "2023-01-01", "Комментарий", List.of(Color.GREY)},
                {"Имя", "Фамилия", "какой то адрес дом 14", 6, "89111660000", 3, "2023-01-01", "Комментарий", List.of(Color.GREY, Color.BLACK)},
                {"Имя", "Фамилия", "какой то адрес дом 14", 0, "89111660000", 0, "2024-01-01", "Комментарий", null},
        };
    }

    @Test
    @DisplayName("Успешное создание ордера")
    public void orderCreateHappyPath() {
        Order orderBody = new Order(
                firstName,
                lastName,
                address,
                metroStation,
                phone,
                rentTime,
                deliveryDate,
                comment,
                color
        );

        Response response = sendPostRequest(orderBody, ORDERS_PATH);
        checkOrderIsCreated(response);
        trackId = getTrackId(response);
    }
    @After
    public void cancelOrder(){
        cancelOrderByTrackId(trackId);
    }
}
