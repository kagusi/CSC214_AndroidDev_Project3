package assignment02.csc214.project3.Models;

import java.io.Serializable;

/**
 * Created by Kennedy on 5/2/2017.
 */

public class CardInfo implements Serializable {

    private String mUsername;
    private String mName;
    private String mCardNum;
    private String mExpDate;
    private String mCVV;

    public CardInfo(String username, String name, String cardNum, String expDate, String CVV) {
        mUsername = username;
        mName = name;
        mCardNum = cardNum;
        mExpDate = expDate;
        mCVV = CVV;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getCardNum() {
        return mCardNum;
    }

    public void setCardNum(String cardNum) {
        mCardNum = cardNum;
    }

    public String getExpDate() {
        return mExpDate;
    }

    public void setExpDate(String expDate) {
        mExpDate = expDate;
    }

    public String getCVV() {
        return mCVV;
    }

    public void setCVV(String CVV) {
        mCVV = CVV;
    }
}
