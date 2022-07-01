package DataUtility;

import java.util.ArrayList;

public class User {
    private final String name;
    private String email;
    private String Password;
    private float Balance;
    private String phone;
    private final Cart mCart;
    private final ArrayList<Order> orders = new ArrayList<Order>();

    public User(String name, String email, String password, String phone, float balance) {
        this.name = name;
        this.email = email;
        Password = password;
        Balance = balance;
        this.phone = phone;
        mCart = new Cart();
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return Password;
    }

    public float getBalance() {
        return Balance;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setBalance(int balance) {
        Balance = balance;
    }

    public void addToCart(Item item, int count){
        mCart.addItem(item, count);
    }

    public void makeOrder(Order order){
        orders.add(order);
    }
}
