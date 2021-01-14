package foodwarehouse.database.tables;

final public class OrderTable {
    static final public String NAME = "ORDER";

    static final public class Columns {
        static final public String ORDER_ID = "ORDER_ID";
        static final public String PAYMENT_ID = "PAYMENT_ID";
        static final public String CUSTOMER_ID = "CUSTOMER_ID";
        static final public String DELIVERY_ID = "DELIVERY_ID";
        static final public String COMMENT = "COMMENT";
        static final public String ORDER_STATE = "ORDER_STATE";
        static final public String ORDER_DATE = "ORDER_DATE";
    }

    static final public class Procedures {
        static final public String INSERT = "CALL `INSERT_ORDER`(?,?,?,?,?)";

        static final public String UPDATE_STATE = "CALL `UPDATE_ORDER_STATE`(?,?)";
        static final public String UPDATE_PAYMENT = "CALL `UPDATE_ORDER_STATE`(?,?)";

        static final public String DELETE = "CALL `DELETE_ORDER`(?)";

        static final public String READ_ALL = "CALL `GET_ORDERS`()";
        static final public String READ_BY_ID = "CALL `GET_ORDER_BY_ID`(?)";
        static final public String READ_CUSTOMER_ALL = "CALL `GET_CUSTOMER_ORDERS`(?)";

        static final public String STATISTICS = "CALL `ORDER_STATISTICS`(?,?)";
        static final public String FIND_BETWEEN_DATES = "CALL `ORDERS_BETWEEN`(?,?)";
    }
}
