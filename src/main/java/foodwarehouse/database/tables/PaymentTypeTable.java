package foodwarehouse.database.tables;

final public class PaymentTypeTable {
    static final public String NAME = "PAYMENT_TYPE";

    static final public class Columns {
        static final public String TYPE_ID = "PAYMENT_TYPE_ID";
        static final public String TYPE = "PAYMENT_TYPE";
    }

    static final public class Procedures {
        static final public String INSERT = "CALL `INSERT_PAYMENT_TYPE`(?,?)";

        static final public String UPDATE = "CALL `UPDATE_PAYMENT_TYPE`(?,?)";

        static final public String DELETE = "CALL `DELETE_PAYMENT_TYPE`(?)";

        static final public String READ_ALL = "CALL `GET_PAYMENT_TYPES`()";
        static final public String READ_BY_ID = "CALL `GET_PAYMENT_TYPE_BY_ID`(?)";
    }
}