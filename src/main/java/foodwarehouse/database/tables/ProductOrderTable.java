package foodwarehouse.database.tables;

final public class ProductOrderTable {
    static final public String NAME = "PRODUCT_ORDER";

    static final public class Columns {
        static final public String ORDER_ID = "ORDER_ID";
        static final public String BATCH_ID = "BATCH_ID";
        static final public String QUANTITY = "QUANTITY";
    }

    static final public class Procedures {
        static final public String INSERT = "CALL `INSERT_PRODUCT_ORDER`(?,?,?)";

        static final public String UPDATE = "CALL `UPDATE_PRODUCT_ORDER`(?,?,?)";

        static final public String DELETE = "CALL `DELETE_PRODUCT_ORDER`(?,?)";

        static final public String READ_ALL = "CALL `GET_PRODUCT_ORDER_ALL`()";
        static final public String READ_BY_ID = "CALL `GET_PRODUCT_ORDER_BY_ID`(?,?)";
        static final public String READ_BY_ORDER_ID = "CALL `GET_PRODUCT_ORDER_BY_ORDER_ID`(?)";
    }
}
