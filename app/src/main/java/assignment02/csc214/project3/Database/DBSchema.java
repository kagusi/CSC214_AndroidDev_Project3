package assignment02.csc214.project3.Database;

/**
 * Created by Kennedy on 5/2/2017.
 */

public class DBSchema {

    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "jetshopper_database.db";

    public static final class AccountTable {
        public static final String NAME = "account";
        public static final class Cols {
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
            public static final String SECURITYQUESTION = "securityquestion";
            public static final String ACCOUNTYPE = "acount_typer";
            public static final String DATEJOINED = "datejoined";
        }
    }

    public static final class DriverProfileTable {
        public static final String NAME = "driverprofile";
        public static final class Cols {
            public static final String USERNAME = "username";
            public static final String FULLNAME = "fullname";
            public static final String PROFILEPICLOCATION = "profilepiclocation";
            public static final String SSN = "ssn";
            public static final String DOB = "dob";
            public static final String PHONE = "phone";
            public static final String ADDRESS = "address";
        }
    }

    public static final class DeliveryTable {
        public static final String NAME = "deliveries";
        public static final class Cols {
            public static final String ORDERID = "orderid";
            public static final String USERNAME = "username";
            public static final String DATEDELIVERED = "datedelivered";
            public static final String CUSTOMERNAME = "customername";
            public static final String COMMISSION = "commission";
            public static final String ISCOMPLETED = "iscompleted";
        }
    }

    public static final class ShippingAddTable {
        public static final String NAME = "shippingaddress";
        public static final class Cols {
            public static final String USERNAME = "username";
            public static final String FULLNAME = "fullname";
            public static final String ADDRESS1 = "address1";
            public static final String ADDRESS2 = "address2";
            public static final String CITY = "city";
            public static final String STATE = "state";
            public static final String PHONE = "phone";
        }
    }

    public static final class CardInfoTable {
        public static final String NAME = "cardinfo";
        public static final class Cols {
            public static final String USERNAME = "username";
            public static final String FULLNAME = "fullname";
            public static final String CARDNUMBER = "cardnumber";
            public static final String EXPDATE = "expdate";
            public static final String CVV = "cvv";
        }
    }

    public static final class OrderTable {
        public static final String NAME = "orderinfo";
        public static final class Cols {
            public static final String ORDERDATE = "orderdate";
            public static final String ORDERID = "orderid";
            public static final String USERNAME = "username";
            public static final String ITEMSORDERED = "itemsordered";
            public static final String DELIVERYDRIVER = "deliverydriver";
            public static final String AMOUNT = "amount";
            public static final String ISCOMPLETED = "iscompleted";
        }
    }
}
