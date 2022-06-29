package DataUtility;

import java.util.Date;

public class Order extends Container {
    private final Date dateCreated;

    public Order(Date dateCreated){
        this.dateCreated = dateCreated;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
}
