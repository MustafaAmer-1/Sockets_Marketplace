package DataUtility;

import java.util.ArrayList;
import java.util.Arrays;

public class Item {
    private String name;
    private float price;
    private int quantity;
    private String imgURL;
    private ArrayList<String> categories;

    public Item(String name, float price, int quantity, String imgURL, String ... categories) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imgURL = imgURL;
        this.categories = new ArrayList<>(Arrays.asList(categories));
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

    public String[] getCategories() {
        return (String[]) categories.toArray();
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

    public void addToCategory(String category){
        this.categories.add(category);
    }

    public void removeFromCategory(String category){
        this.categories.remove(category);
    }
}
