package foodwarehouse.database.tables;

final public class DeliveryTable {
    static final public String NAME = "DELIVERY";

    static final public class Columns {
        static final public String DELIVERY_ID = "DELIVERY_ID";
        static final public String ADDRESS_ID = "ADDRESS_ID";
        static final public String SUPPLIER_ID = "SUPPLIER_ID";
        static final public String REMOVAL_DATE = "REMOVAL_FROM_STORAGE_DATE";
        static final public String DELIVERY_DATE = "DELIVERY_DATE";
    }

    static final public class Procedures {
        static final public String INSERT = "CALL `INSERT_DELIVERY`(?,?,?)";

        static final public String UPDATE_REMOVE = "CALL `UPDATE_DELIVERY_REMOVE_FROM_STORAGE`(?,?)";
        static final public String UPDATE_COMPLETE = "CALL `UPDATE_DELIVERY_COMPLETE`(?,?)";

        static final public String DELETE = "CALL `DELETE_DELIVERY`(?)";

        static final public String READ_ALL = "CALL `GET_DELIVERIES`()";
        static final public String READ_BY_ID = "CALL `GET_DELIVERY_BY_ID`(?)";
    }
}
