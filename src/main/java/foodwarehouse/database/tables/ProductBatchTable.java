package foodwarehouse.database.tables;

final public class ProductBatchTable {
    static final public String NAME = "PRODUCT_BATCH";

    static final public class Columns {
        static final public String BATCH_ID = "BATCH_ID";
        static final public String PRODUCT_ID = "PRODUCT_ID";
        static final public String BATCH_NO = "BATCH_NO";
        static final public String EAT_BY_DATE = "EAT_BY_DATE";
        static final public String DISCOUNT = "DISCOUNT";
        static final public String PACKAGES_QUANTITY = "PACKAGES_QUANTITY";
    }

    static final public class Procedures {
        static final public String INSERT = "CALL `INSERT_PRODUCT_BATCH`(?,?,?,?,?)";

        static final public String UPDATE_EAT_BY_DATE = "CALL `UPDATE_PRODUCT_BATCH_EAT_BY_DATE`(?,?)";

        static final public String READ_BY_ID = "CALL `GET_PRODUCT_BATCH_BY_PRODUCT_ID`(?)";
    }
}
