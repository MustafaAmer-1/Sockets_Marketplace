package DataUtility;

import java.util.ArrayList;
import java.util.Date;

public class Order<Arraylist> {
    //private final Date dateCreated;
    private final int OID;
    private final int CustID;
    private String name;
    private int quantity;
    private final float total_price;
    private ArrayList<Item> items = new ArrayList();
    public Order(int oid,int custid,float price){

        this.OID = oid;
        this.CustID=custid;
        this.total_price=price;
    }
    public int getOID() {
        return OID;
    }
    public void additem(int PID,String name, float price, int quantity, String imgURL, String categorie)
    {
        items.add(new Item( PID,name,price, quantity,  imgURL, categorie));
        this.quantity=quantity;
        this.name = name;
    }
    public ArrayList<Item> getItems() {
        return items;
    }

    public float getTotal_price() {
        return total_price;
    }

}