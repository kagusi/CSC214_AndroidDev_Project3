package assignment02.csc214.project3.Models;

import java.io.Serializable;

/**
 * Created by Kennedy on 5/2/2017.
 */

public class ShippingAddress implements Serializable {

    private String mUsername;
    private String mFullname;
    private String mAddress1;
    private String mAddress2;
    private String mCity;
    private String mState;
    private String mPhone;

    public ShippingAddress(String fullname, String address1, String address2, String city, String state, String phone, String username) {
        mFullname = fullname;
        mAddress1 = address1;
        mAddress2 = address2;
        mCity = city;
        mState = state;
        mPhone = phone;
        mUsername = username;
    }

    public String getFullname() {
        return mFullname;
    }

    public void setFullname(String fullname) {
        mFullname = fullname;
    }

    public String getAddress1() {
        return mAddress1;
    }

    public void setAddress1(String address1) {
        mAddress1 = address1;
    }

    public String getAddress2() {
        return mAddress2;
    }

    public void setAddress2(String address2) {
        mAddress2 = address2;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }
}
