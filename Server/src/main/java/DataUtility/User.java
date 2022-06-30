package DataUtility;

import java.util.ArrayList;

public class User {
    private final String name;
    private String Username;
    private String Password;
    private float Balance;
    private final Cart mCart;
    private final ArrayList<Order> orders = new ArrayList<Order>();

    public User(String name, String username, String password, float balance) {
        this.name = name;
        Username = username;
        Password = password;
        Balance = balance;
        mCart = new Cart();
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public float getBalance() {
        return Balance;
    }

    public void setUsername(String username) {
        Username = username;
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
