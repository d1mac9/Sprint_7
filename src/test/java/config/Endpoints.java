package config;

public class Endpoints {
    public static final String BASE_URL = "http://qa-scooter.praktikum-services.ru";
    public static final String BASE_PATH = "/api/v1";
    public static final String COURIER_PATH = "/courier";
    public static final String COURIER_ID_PATH = "/courier/{id}";
    public static final String COURIER_LOGIN_PATH = "/courier/login";
    public static final String ORDERS_PATH = "/orders";
    public static final String ORDERS_ACCEPT = "/orders/accept/";
    public static final String ORDERS_CANCEL = "/orders/cancel";
    public static final String ORDERS_TRACK = "/orders/track?t={trackId}";
    public static final String ORDER_TRACK_NULL = "/orders/track?t=";

}
