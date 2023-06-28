package helper;

import model.Courier;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.Random;

import static config.TestData.*;

public class CourierGenerator {
    private static final Random rnd = new Random();
    public static int randomInt = RandomUtils.nextInt(9000000,9999999);

    public static Courier randomCourier() {
        return new Courier(LOGIN + RandomStringUtils.randomAlphanumeric(5, 10), PASSWORD, FIRST_NAME);
    }
}
