package helper;

import model.Order;

public class OrderGenerator {
    public static Order genericOrder(){
        return new Order("Имя", "Фамилия", "какой то адрес дом 14", 0,
                "89111660000", 0, "2024-01-01", "Комментарий", null);
    }
}
