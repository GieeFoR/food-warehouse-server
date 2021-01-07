package foodwarehouse.database.tables;

final public class PaymentTable {
    static final public String NAME = "PAYMENT";

    static final public class Columns {
        static final public String PAYMENT_ID = "PAYMENT_ID";
        static final public String PAYMENT_TYPE_ID = "PAYMENT_TYPE_ID";
        static final public String VALUE = "PAYMENT_VALUE";
        static final public String STATE = "PAYMENT_STATE";
    }

    static final public class Procedures {
        static final public String INSERT = "CALL `INSERT_PAYMENT`(?,?,?)";

        static final public String UPDATE_VALUE = "CALL `UPDATE_PAYMENT_VALUE`(?,?)";
        static final public String COMPLETE = "CALL `COMPLETE_PAYMENT`(?)";

        static final public String DELETE = "CALL `DELETE_PAYMENT`(?)";

        static final public String READ_ALL = "CALL `GET_PAYMENTS`()";
        static final public String READ_BY_ID = "CALL `GET_PAYMENT_BY_ID`(?)";
    }
}
