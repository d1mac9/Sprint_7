package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private Integer metroStation;
    private String phone;
    private Integer rentTime;
    private String deliveryDate;
    private String comment;
    private List<Color> color;


    public Order(String firstName,
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

    public Order(){}
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getMetroStation() {
        return metroStation;
    }

    public void setMetroStation(Integer metroStation) {
        this.metroStation = metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRentTime() {
        return rentTime;
    }

    public void setRentTime(Integer rentTime) {
        this.rentTime = rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Color> getColor() {
        return color;
    }

    public void setColor(List<Color> color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (!Objects.equals(firstName, order.firstName)) return false;
        if (!Objects.equals(lastName, order.lastName)) return false;
        if (!Objects.equals(address, order.address)) return false;
        if (!Objects.equals(metroStation, order.metroStation)) return false;
        if (!Objects.equals(phone, order.phone)) return false;
        if (!Objects.equals(rentTime, order.rentTime)) return false;
        if (!Objects.equals(deliveryDate, order.deliveryDate)) return false;
        if (!Objects.equals(comment, order.comment)) return false;
        return Objects.equals(color, order.color);
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (metroStation != null ? metroStation.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (rentTime != null ? rentTime.hashCode() : 0);
        result = 31 * result + (deliveryDate != null ? deliveryDate.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}
