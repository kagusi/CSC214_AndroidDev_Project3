package assignment02.csc214.project3.Models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kennedy on 5/2/2017.
 */

public class Order implements Serializable {

    private String mOrderID;
    private String mUsername;
    private String mItemsOrdered;
    private String mOrderDate;
    private String mDeliveryDriver;
    private String mAmount;
    private boolean mIsCompleted;

    public Order(String orderID, String username, String itemsOrdered, String deliveryDriver, String amount) {
        mOrderID = orderID;
        mUsername = username;
        mItemsOrdered = itemsOrdered;
        mDeliveryDriver = deliveryDriver;
        mAmount = amount;
        mIsCompleted = false;
        DateFormat dateformat = new SimpleDateFormat("dd/MM/yy");
        Date currentDate = new Date();
        mOrderDate = dateformat.format(currentDate);
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getItemsOrdered() {
        return mItemsOrdered;
    }

    public void setItemsOrdered(String itemsOrdered) {
        mItemsOrdered = itemsOrdered;
    }

    public String getOrderDate() {
        return mOrderDate;
    }

    public void setOrderDate(String orderDate) {
        mOrderDate = orderDate;
    }

    public String getDeliveryDriver() {
        return mDeliveryDriver;
    }

    public void setDeliveryDriver(String deliveryDriver) {
        mDeliveryDriver = deliveryDriver;
    }

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String amount) {
        mAmount = amount;
    }

    public boolean isCompleted() {
        return mIsCompleted;
    }

    public void setCompleted(boolean completed) {
        mIsCompleted = completed;
    }

    public String getOrderID() {
        return mOrderID;
    }

    public void setOrderID(String orderID) {
        mOrderID = orderID;
    }
}
