package foodwarehouse.database.tables;

final public class ProductInStorageTable {
    static final public String NAME = "PRODUCT_IN_STORAGE";

    static final public class Columns {
        static final public String BATCH_ID = "BATCH_ID";
        static final public String STORAGE_ID = "STORAGE_ID";
        static final public String QUANTITY = "QUANTITY";
    }

    static final public class Procedures {
        static final public String INSERT = "CALL `INSERT_PRODUCT_IN_STORAGE`(?,?,?)";

        static final public String UPDATE = "CALL `UPDATE_PRODUCT_IN_STORAGE`(?,?,?)";

        static final public String DELETE = "CALL `DELETE_PRODUCT_IN_STORAGE`(?,?)";

        static final public String READ_ALL = "CALL `GET_PRODUCT_IN_STORAGE_ALL`()";
        static final public String READ_BY_ID = "CALL `GET_PRODUCT_IN_STORAGE_BY_PRODUCT_ID`(?,?)";
    }
}
