package foodwarehouse.database.tables;

final public class CustomerTable {
    static final public String NAME = "CUSTOMER";

    static final public class Columns {
        static final public String CUSTOMER_ID = "CUSTOMER_ID";
        static final public String USER_ID = "USER_ID";
        static final public String ADDRESS_ID = "ADDRESS_ID";
        static final public String NAME = "NAME";
        static final public String SURNAME = "SURNAME";
        static final public String FIRMNAME = "FIRM_NAME";
        static final public String PHONE = "TELEPHONE_NO";
        static final public String TAX = "TAX_ID";
        static final public String DISCOUNT = "DISCOUNT";
    }

    static final public class Procedures {
        static final public String INSERT = "CALL `INSERT_CUSTOMER`(?,?,?,?,?,?,?,?)";

        static final public String UPDATE = "CALL `UPDATE_CUSTOMER`(?,?,?,?,?,?,?)";

        static final public String DELETE = "CALL `DELETE_CUSTOMER`(?)";

        static final public String READ_ALL = "CALL `GET_CUSTOMERS`()";
        static final public String READ_BY_ID = "CALL `GET_CUSTOMER_BY_ID`(?)";
        static final public String READ_BY_USERNAME = "CALL `GET_CUSTOMER_BY_USERNAME`(?)";
    }
}
