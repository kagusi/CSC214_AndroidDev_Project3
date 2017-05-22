package assignment02.csc214.project3.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import assignment02.csc214.project3.Database.DBSchema.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import assignment02.csc214.project3.Models.*;

/**
 * Created by Kennedy on 5/2/2017.
 */

public class ModelQueryManager {

    private static ModelQueryManager MODELMANAGER;

    private final Context mContext;
    private final SQLiteDatabase mDatabase;

    private ModelQueryManager(Context context){

        mContext = context.getApplicationContext();
        mDatabase = new DBHelper(mContext).getWritableDatabase();

    }

    public static ModelQueryManager get(Context context) {
        MODELMANAGER = new ModelQueryManager(context);
        return MODELMANAGER;
    }

    //Queries database for an account with the supplied username
    public Account login(String[] username){
        Account account;
        Cursor cursor = mDatabase.query(
                AccountTable.NAME, // table name
                null,           // which columns; null for all
                "username = ?",          // where clause, e.g. id=?
                username,       // where arguments
                null,       // group by
                null,       // having
                null        // order by
        );
        if(cursor == null)
            return null;
        else
        {
            DBCursorWrapper wrapper = new DBCursorWrapper(cursor);

            try {
                wrapper.moveToFirst();
                if(wrapper.getCount() == 0)
                    return null;
                else
                    account = wrapper.getAccount();
            }
            finally {
                wrapper.close();
            }
            return account;
        }

    }

    //Insert account into Database
    public void createAccount(Account account)
    {
        ContentValues values = getAccountContentvalues(account);
        mDatabase.insert(AccountTable.NAME, null, values);
    }

    //Create an account Contentvalues
    private static ContentValues getAccountContentvalues(Account account) {

        ContentValues values = new ContentValues();
        values.put(AccountTable.Cols.USERNAME, account.getUsername());
        values.put(AccountTable.Cols.PASSWORD, account.getPassword());
        values.put(AccountTable.Cols.SECURITYQUESTION, account.getSecurityQuestion());
        values.put(AccountTable.Cols.ACCOUNTYPE, account.getAccountType());
        values.put(AccountTable.Cols.DATEJOINED, account.getDateJoined());
        return values;
    }

    //Create profile
    public void createDriverProfile(DriverProfile profile)
    {
        ContentValues values = getDriverProfileContentvalues(profile);
        mDatabase.insert(DriverProfileTable.NAME, null, values);
    }

    //Create an profle Contentvalues
    private static ContentValues getDriverProfileContentvalues(DriverProfile profile) {

        ContentValues values = new ContentValues();
        values.put(DriverProfileTable.Cols.USERNAME, profile.getDriverUsername());
        values.put(DriverProfileTable.Cols.FULLNAME, profile.getFullName());
        values.put(DriverProfileTable.Cols.PROFILEPICLOCATION, profile.getProfilePicLocation());
        values.put(DriverProfileTable.Cols.SSN, profile.getSSN());
        values.put(DriverProfileTable.Cols.DOB, profile.getDOB());
        values.put(DriverProfileTable.Cols.PHONE, profile.getPhone());
        values.put(DriverProfileTable.Cols.ADDRESS, profile.getAddress());

        return values;
    }

    //Queries database for a profile with the supplied username
    public DriverProfile getDriverProfile(String[] username){
        DriverProfile profile;
        Cursor cursor = mDatabase.query(
                DriverProfileTable.NAME, // table name
                null,           // which columns; null for all
                DriverProfileTable.Cols.USERNAME + "=?",          // where clause, e.g. id=?
                username,       // where arguments
                null,       // group by
                null,       // having
                null        // order by

        );

        DBCursorWrapper wrapper = new DBCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();
            if(wrapper.getCount() == 0)
                return null;
            else
                profile = wrapper.getDriverProfile();
        }
        finally {
            wrapper.close();
        }

        return profile;

    }

    //Update Profile
    public void updateDriverProfile(DriverProfile profile)
    {
        String[] uname = new String[1];
        uname[0] = profile.getDriverUsername();
        ContentValues values = getDriverProfileContentvalues(profile);

        mDatabase.update(DriverProfileTable.NAME,
                values,
                DriverProfileTable.Cols.USERNAME + "=?",
                uname);
    }

    //Create profile
    public void createShippingAddress(ShippingAddress shipAdd)
    {
        ContentValues values = getShippingAddContentvalues(shipAdd);
        mDatabase.insert(ShippingAddTable.NAME, null, values);
    }

    //Create an profle Contentvalues
    private static ContentValues getShippingAddContentvalues(ShippingAddress shipAdd) {

        ContentValues values = new ContentValues();
        values.put(ShippingAddTable.Cols.USERNAME, shipAdd.getUsername());
        values.put(ShippingAddTable.Cols.FULLNAME, shipAdd.getFullname());
        values.put(ShippingAddTable.Cols.ADDRESS1, shipAdd.getAddress1());
        values.put(ShippingAddTable.Cols.ADDRESS2, shipAdd.getAddress2());
        values.put(ShippingAddTable.Cols.CITY, shipAdd.getCity());
        values.put(ShippingAddTable.Cols.STATE, shipAdd.getState());
        values.put(ShippingAddTable.Cols.PHONE, shipAdd.getPhone());

        return values;
    }

    //Queries database for a profile with the supplied username
    public ShippingAddress getShippingAdd(String[] username){
        ShippingAddress shipAdd;
        Cursor cursor = mDatabase.query(
                ShippingAddTable.NAME, // table name
                null,           // which columns; null for all
                ShippingAddTable.Cols.USERNAME + "=?",          // where clause, e.g. id=?
                username,       // where arguments
                null,       // group by
                null,       // having
                null        // order by

        );

        DBCursorWrapper wrapper = new DBCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();
            if(wrapper.getCount() == 0)
                return null;
            else
                shipAdd = wrapper.getShippingAdd();
        }
        finally {
            wrapper.close();
        }

        return shipAdd;

    }

    //Update Profile
    public void updateShippingAddress(ShippingAddress shippingAddress)
    {
        String[] uname = new String[1];
        uname[0] = shippingAddress.getUsername();
        ContentValues values = getShippingAddContentvalues(shippingAddress);

        mDatabase.update(ShippingAddTable.NAME,
                values,
                ShippingAddTable.Cols.USERNAME + "=?",
                uname);
    }

    //Create card info
    public void createCardInfo(CardInfo cardInfo)
    {
        ContentValues values = getCardInfoContentvalues(cardInfo);
        mDatabase.insert(CardInfoTable.NAME, null, values);
    }

    private static ContentValues getCardInfoContentvalues(CardInfo cardInfo) {

        ContentValues values = new ContentValues();
        values.put(CardInfoTable.Cols.USERNAME, cardInfo.getUsername());
        values.put(CardInfoTable.Cols.FULLNAME, cardInfo.getName());
        values.put(CardInfoTable.Cols.CARDNUMBER, cardInfo.getCardNum());
        values.put(CardInfoTable.Cols.EXPDATE, cardInfo.getExpDate());
        values.put(CardInfoTable.Cols.CVV, cardInfo.getCVV());

        return values;
    }

    public CardInfo getCardInfo(String[] username){
        CardInfo cardInfo;
        Cursor cursor = mDatabase.query(
                CardInfoTable.NAME, // table name
                null,           // which columns; null for all
                CardInfoTable.Cols.USERNAME + "=?",          // where clause, e.g. id=?
                username,       // where arguments
                null,       // group by
                null,       // having
                null        // order by

        );

        DBCursorWrapper wrapper = new DBCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();
            if(wrapper.getCount() == 0)
                return null;
            else
                cardInfo = wrapper.getCardInfo();
        }
        finally {
            wrapper.close();
        }

        return cardInfo;
    }

    public void updateCardInfo(CardInfo cardInfo)
    {
        String[] uname = new String[1];
        uname[0] = cardInfo.getUsername();
        ContentValues values = getCardInfoContentvalues(cardInfo);

        mDatabase.update(CardInfoTable.NAME,
                values,
                CardInfoTable.Cols.USERNAME + "=?",
                uname);
    }

    public void createOrder(Order order)
    {
        ContentValues values = getOrderContentvalues(order);
        mDatabase.insert(OrderTable.NAME, null, values);
    }

    private static ContentValues getOrderContentvalues(Order order) {

        ContentValues values = new ContentValues();
        values.put(OrderTable.Cols.USERNAME, order.getUsername());
        values.put(OrderTable.Cols.ORDERID, order.getOrderID());
        values.put(OrderTable.Cols.ITEMSORDERED, order.getItemsOrdered());
        values.put(OrderTable.Cols.ORDERDATE, order.getOrderDate());
        values.put(OrderTable.Cols.DELIVERYDRIVER, order.getDeliveryDriver());
        values.put(OrderTable.Cols.AMOUNT, order.getAmount());
        values.put(OrderTable.Cols.ISCOMPLETED, order.isCompleted());

        return values;
    }

    public Order getOrder(String[] username){
        Order order;
        Cursor cursor = mDatabase.query(
                OrderTable.NAME, // table name
                null,           // which columns; null for all
                OrderTable.Cols.USERNAME + "=?",          // where clause, e.g. id=?
                username,       // where arguments
                null,       // group by
                null,       // having
                null        // order by

        );

        DBCursorWrapper wrapper = new DBCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();
            if(wrapper.getCount() == 0)
                return null;
            else
                order = wrapper.getOrder();
        }
        finally {
            wrapper.close();
        }

        return order;
    }


    public List<Order> getOrderHistory(String[] username ){

        List<Order> orders = new ArrayList<>();

        Cursor cursor = mDatabase.query(
                OrderTable.NAME, // table name
                null,           // which columns; null for all
                OrderTable.Cols.USERNAME + "=?",          // where clause, e.g. id=?
                username,       // where arguments
                null,       // group by
                null,       // having
                null        // order by

        );

        DBCursorWrapper wrapper = new DBCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();
            while(wrapper.isAfterLast() == false) {
                Order order = wrapper.getOrder();
                orders.add(order);
                wrapper.moveToNext();
            }
        }
        finally {
            cursor.close();
            wrapper.close();
        }

        return orders;

    }


    public DriverProfile getDeliveryDriver(){
        DriverProfile profile;

        String MY_QUERY = "SELECT * FROM "+DriverProfileTable.NAME;

        Cursor cursor = mDatabase.rawQuery(MY_QUERY, null);

        DBCursorWrapper wrapper = new DBCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();
            if(wrapper.getCount() == 0)
                return null;
            else
                profile = wrapper.getDriverProfile();
        }
        finally {
            wrapper.close();
        }

        return profile;

    }


    public ShippingAddress getAnyShippingAdd(){
        ShippingAddress shipAdd;

        String MY_QUERY = "SELECT * FROM "+ShippingAddTable.NAME;

        Cursor cursor = mDatabase.rawQuery(MY_QUERY, null);

        DBCursorWrapper wrapper = new DBCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();
            if(wrapper.getCount() == 0)
                return null;
            else
                shipAdd = wrapper.getShippingAdd();
        }
        finally {
            wrapper.close();
        }

        return shipAdd;

    }


}
