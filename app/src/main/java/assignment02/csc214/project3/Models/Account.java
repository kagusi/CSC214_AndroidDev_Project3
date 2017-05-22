package assignment02.csc214.project3.Models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kennedy on 5/1/2017.
 */

public class Account implements Serializable {

    private String mUsername;
    private String mPassword;
    private String mSecurityQuestion;
    private String mAccountType;
    private String mDateJoined;


    public Account(String username, String password, String securityQuestion, String accountType) {
        mUsername = username;
        mPassword = password;
        mSecurityQuestion = securityQuestion;
        mAccountType = accountType;
        DateFormat dateformat = new SimpleDateFormat("dd/MM/yy");
        Date currentDate = new Date();
        mDateJoined = dateformat.format(currentDate);
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getSecurityQuestion() {
        return mSecurityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        mSecurityQuestion = securityQuestion;
    }

    public String getAccountType() {
        return mAccountType;
    }

    public void setAccountType(String accountType) {
        mAccountType = accountType;
    }

    public String getDateJoined() {
        return mDateJoined;
    }

    public void setDateJoined(String dateJoined) {
        mDateJoined = dateJoined;
    }

    public String verifyPass(String password)
    {
        if(password.equals(mPassword))
            return "success";
        else
            return "failed";
    }

    public String verifySecureQuestion(String secureQuestion)
    {
        if(secureQuestion.equals(mSecurityQuestion))
            return "success";
        else
            return "failed";
    }






}
