package model;

public class CourierCreds {
    private String login;
    private String password;

    public CourierCreds(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public CourierCreds(){}

    public static CourierCreds from(Courier courier){
        CourierCreds cred = new CourierCreds();
        cred.setLogin(courier.getLogin());
        cred.setPassword(courier.getPassword());
        return cred;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
