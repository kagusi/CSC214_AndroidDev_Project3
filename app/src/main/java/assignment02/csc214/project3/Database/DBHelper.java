package assignment02.csc214.project3.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import assignment02.csc214.project3.Database.DBSchema.*;

/**
 * Created by Kennedy on 5/2/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, DBSchema.DATABASE_NAME, null, DBSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + AccountTable.NAME
                + "(accountid integer primary key autoincrement, "
                + AccountTable.Cols.USERNAME + ", "
                + AccountTable.Cols.PASSWORD + ", "
                + AccountTable.Cols.ACCOUNTYPE + ", "
                + AccountTable.Cols.DATEJOINED + ", "
                + AccountTable.Cols.SECURITYQUESTION + ")");

        db.execSQL("create table " + DriverProfileTable.NAME
                + "(driverprofileid integer primary key autoincrement, "
                + DriverProfileTable.Cols.USERNAME+ ", "
                + DriverProfileTable.Cols.FULLNAME+ ", "
                + DriverProfileTable.Cols.PROFILEPICLOCATION+ ", "
                + DriverProfileTable.Cols.SSN+ ", "
                + DriverProfileTable.Cols.DOB+ ", "
                + DriverProfileTable.Cols.PHONE+ ", "
                + DriverProfileTable.Cols.ADDRESS+ ")");

        db.execSQL("create table " + DeliveryTable.NAME
                + "(deliveryid integer primary key autoincrement, "
                + DeliveryTable.Cols.USERNAME+ ", "
                + DeliveryTable.Cols.ORDERID+ ", "
                + DeliveryTable.Cols.DATEDELIVERED+ ", "
                + DeliveryTable.Cols.CUSTOMERNAME+ ", "
                + DeliveryTable.Cols.COMMISSION+ ", "
                + DeliveryTable.Cols.ISCOMPLETED+ ")");

        db.execSQL("create table " + ShippingAddTable.NAME
                + "(shippingaddid integer primary key autoincrement, "
                + ShippingAddTable.Cols.USERNAME+ ", "
                + ShippingAddTable.Cols.FULLNAME+ ", "
                + ShippingAddTable.Cols.ADDRESS1+ ", "
                + ShippingAddTable.Cols.ADDRESS2+ ", "
                + ShippingAddTable.Cols.CITY+ ", "
                + ShippingAddTable.Cols.STATE+ ", "
                + ShippingAddTable.Cols.PHONE+ ")");

        db.execSQL("create table " + CardInfoTable.NAME
                + "(cardinfoid integer primary key autoincrement, "
                + CardInfoTable.Cols.USERNAME+ ", "
                + CardInfoTable.Cols.FULLNAME+ ", "
                + CardInfoTable.Cols.CARDNUMBER+ ", "
                + CardInfoTable.Cols.EXPDATE+ ", "
                + CardInfoTable.Cols.CVV+ ")");

        db.execSQL("create table " + OrderTable.NAME
                + "(ordid integer primary key autoincrement, "
                + OrderTable.Cols.ORDERDATE+ ", "
                + OrderTable.Cols.ORDERID+ ", "
                + OrderTable.Cols.USERNAME+ ", "
                + OrderTable.Cols.ITEMSORDERED+ ", "
                + OrderTable.Cols.DELIVERYDRIVER+ ", "
                + OrderTable.Cols.AMOUNT+ ", "
                + OrderTable.Cols.ISCOMPLETED+ ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
