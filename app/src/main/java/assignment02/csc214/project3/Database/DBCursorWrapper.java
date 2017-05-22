package assignment02.csc214.project3.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import assignment02.csc214.project3.Models.*;
import assignment02.csc214.project3.Database.DBSchema.*;

/**
 * Created by Kennedy on 5/2/2017.
 */

public class DBCursorWrapper extends CursorWrapper {

    Cursor cursor;
    public DBCursorWrapper(Cursor cursor) {
        super(cursor);
        this.cursor = cursor;
    }

    public Account getAccount() {
        String username =
                cursor.getString(cursor.getColumnIndex(AccountTable.Cols.USERNAME));
        String password =
                cursor.getString(cursor.getColumnIndex(AccountTable.Cols.PASSWORD));
        String securityquestion =
                cursor.getString(cursor.getColumnIndex(AccountTable.Cols.SECURITYQUESTION));
        String accountType =
                cursor.getString(cursor.getColumnIndex(AccountTable.Cols.ACCOUNTYPE));

        return new Account(username,password,securityquestion, accountType);
    }

    public DriverProfile getDriverProfile() {

        String username =
                cursor.getString(cursor.getColumnIndex(DriverProfileTable.Cols.USERNAME));
        String fullname =
                cursor.getString(cursor.getColumnIndex(DriverProfileTable.Cols.FULLNAME));
        String profilepiclocation =
                cursor.getString(cursor.getColumnIndex(DriverProfileTable.Cols.PROFILEPICLOCATION));
        String ssn =
                cursor.getString(cursor.getColumnIndex(DriverProfileTable.Cols.SSN));
        String dateofbirth =
                cursor.getString(cursor.getColumnIndex(DriverProfileTable.Cols.DOB));
        String phone =
                cursor.getString(cursor.getColumnIndex(DriverProfileTable.Cols.PHONE));
        String address =
                cursor.getString(cursor.getColumnIndex(DriverProfileTable.Cols.ADDRESS));

        return new DriverProfile(profilepiclocation, fullname, ssn, dateofbirth, phone, address, username);
    }

    public Deliveries getDeliveries() {
        String orderId =
                cursor.getString(cursor.getColumnIndex(DeliveryTable.Cols.ORDERID));
        String username =
                cursor.getString(cursor.getColumnIndex(DeliveryTable.Cols.USERNAME));
        String datedelivered =
                cursor.getString(cursor.getColumnIndex(DeliveryTable.Cols.DATEDELIVERED));
        String customername =
                cursor.getString(cursor.getColumnIndex(DeliveryTable.Cols.CUSTOMERNAME));
        String commission =
                cursor.getString(cursor.getColumnIndex(DeliveryTable.Cols.COMMISSION));
        String iscompleted =
                cursor.getString(cursor.getColumnIndex(DeliveryTable.Cols.ISCOMPLETED));

            return new Deliveries(orderId, customername, username) ;
    }

    public ShippingAddress getShippingAdd() {
        String username =
                cursor.getString(cursor.getColumnIndex(ShippingAddTable.Cols.USERNAME));
        String fullname =
                cursor.getString(cursor.getColumnIndex(ShippingAddTable.Cols.FULLNAME));
        String address1 =
                cursor.getString(cursor.getColumnIndex(ShippingAddTable.Cols.ADDRESS1));
        String address2 =
                cursor.getString(cursor.getColumnIndex(ShippingAddTable.Cols.ADDRESS2));
        String city =
                cursor.getString(cursor.getColumnIndex(ShippingAddTable.Cols.CITY));
        String state =
                cursor.getString(cursor.getColumnIndex(ShippingAddTable.Cols.STATE));
        String phone =
                cursor.getString(cursor.getColumnIndex(ShippingAddTable.Cols.PHONE));

            return new ShippingAddress(fullname, address1, address2, city, state, phone, username);
    }

    public Order getOrder() {
        String orderId =
                cursor.getString(cursor.getColumnIndex(OrderTable.Cols.ORDERID));
        String username =
                cursor.getString(cursor.getColumnIndex(OrderTable.Cols.USERNAME));
        String itemsOrdered =
                cursor.getString(cursor.getColumnIndex(OrderTable.Cols.ITEMSORDERED));
        String orderedDate =
                cursor.getString(cursor.getColumnIndex(OrderTable.Cols.ORDERDATE));
        String deliveryDriver =
                cursor.getString(cursor.getColumnIndex(OrderTable.Cols.DELIVERYDRIVER));
        String amount =
                cursor.getString(cursor.getColumnIndex(OrderTable.Cols.AMOUNT));
        String iscompleted =
                cursor.getString(cursor.getColumnIndex(OrderTable.Cols.ISCOMPLETED));

        return new Order(orderId, username, itemsOrdered, deliveryDriver, amount);
    }


    public CardInfo getCardInfo() {
        String username =
                cursor.getString(cursor.getColumnIndex(CardInfoTable.Cols.USERNAME));
        String fullname =
                cursor.getString(cursor.getColumnIndex(CardInfoTable.Cols.FULLNAME));
        String cardnumber =
                cursor.getString(cursor.getColumnIndex(CardInfoTable.Cols.CARDNUMBER));
        String expDate =
                cursor.getString(cursor.getColumnIndex(CardInfoTable.Cols.EXPDATE));
        String cvv =
                cursor.getString(cursor.getColumnIndex(CardInfoTable.Cols.CVV));

            return new CardInfo(username, fullname, cardnumber, expDate, cvv);
    }
}
