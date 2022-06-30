package DataUtility;

import java.util.ArrayList;
import java.util.Arrays;

public class Item {
    private String name;
    private float price;
    private int quantity;
    private String imgURL;
    private String category;

    public Item(String name, float price, int quantity, String imgURL, String category) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imgURL = imgURL;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
