package assignment02.csc214.project3.Models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kennedy on 5/2/2017.
 */

public class Deliveries implements Serializable {

    private String mOrderID;
    private String mDriverUsername;
    private String mDateDelivered;
    private String mCustomerName;
    private int mCommission;
    private boolean mIsCompleted;

    public Deliveries(String orderID, String customerName, String username) {
        mOrderID = orderID;
        DateFormat dateformat = new SimpleDateFormat("dd/MM/yy");
        Date currentDate = new Date();
        mDateDelivered = dateformat.format(currentDate);
        mCustomerName = customerName;
        mCommission = 0;
        mIsCompleted = false;
        mDriverUsername = username;
    }

    public String getDateDelivered() {
        return mDateDelivered;
    }

    public void setDateDelivered(String dateDelivered) {
        mDateDelivered = dateDelivered;
    }

    public String getCustomerName() {
        return mCustomerName;
    }

    public void setCustomerName(String customerName) {
        mCustomerName = customerName;
    }

    public int getCommission() {
        return mCommission;
    }

    public void setCommission(int commission) {
        mCommission = commission;
    }

    public boolean isCompleted() {
        return mIsCompleted;
    }

    public void setCompleted(boolean completed) {
        mIsCompleted = completed;
    }

    public String getDriverUsername() {
        return mDriverUsername;
    }

    public void setDriverUsername(String driverUsername) {
        mDriverUsername = driverUsername;
    }

    public String getOrderID() {
        return mOrderID;
    }

    public void setOrderID(String orderID) {
        mOrderID = orderID;
    }
}
