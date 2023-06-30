package helper;

import model.Courier;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import static config.TestData.*;

public class CourierGenerator {
    public static int randomInt = RandomUtils.nextInt(9000000,9999999);

    public static Courier randomCourier() {
        return new Courier(LOGIN + RandomStringUtils.randomAlphanumeric(5, 10), PASSWORD, FIRST_NAME);
    }
}
