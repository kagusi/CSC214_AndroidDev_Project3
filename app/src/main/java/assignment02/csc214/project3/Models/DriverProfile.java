package assignment02.csc214.project3.Models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kennedy on 5/2/2017.
 */

public class DriverProfile implements Serializable {

    private String mDriverUsername;
    private String mProfilePicLocation;
    private String mFullName;
    private String mSSN;
    private String mDOB;
    private String mPhone;
    private String mAddress;

    public DriverProfile(String profilePicLocation, String fullName, String SSN, String DOB, String phone, String address, String username) {
        mProfilePicLocation = profilePicLocation;
        mFullName = fullName;
        mSSN = SSN;
        mDOB = DOB;
        mPhone = phone;
        mAddress = address;
        mDriverUsername = username;

    }

    public String getProfilePicLocation() {
        return mProfilePicLocation;
    }

    public void setProfilePicLocation(String profilePicLocation) {
        mProfilePicLocation = profilePicLocation;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public String getSSN() {
        return mSSN;
    }

    public void setSSN(String SSN) {
        mSSN = SSN;
    }

    public String getDOB() {
        return mDOB;
    }

    public void setDOB(String DOB) {
        mDOB = DOB;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getDriverUsername() {
        return mDriverUsername;
    }

    public void setDriverUsername(String driverUsername) {
        mDriverUsername = driverUsername;
    }
}
