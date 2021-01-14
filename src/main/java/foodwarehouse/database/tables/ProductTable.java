package foodwarehouse.database.tables;

final public class ProductTable {
    static final public String NAME = "PRODUCT";

    static final public class Columns {
        static final public String PRODUCT_ID = "PRODUCT_ID";
        static final public String MAKER_ID = "MAKER_ID";
        static final public String PRODUCT_NAME = "PRODUCT_NAME";
        static final public String SHORT_DESC = "SHORT_DESCRIPTION";
        static final public String LONG_DESC = "LONG_DESCRIPTION";
        static final public String CATEGORY = "CATEGORY";
        static final public String NEED_COLD = "NEED_COLD_STORAGE";
        static final public String BUY_PRICE = "BUY_PRICE";
        static final public String SELL_PRICE = "SELL_PRICE";
        static final public String IMAGE = "IMAGE";
    }

    static final public class Procedures {
        static final public String INSERT = "CALL `INSERT_PRODUCT`(?,?,?,?,?,?,?,?,?,?)";

        static final public String UPDATE = "CALL `UPDATE_PRODUCT`(?,?,?,?,?,?,?,?,?)";

        static final public String DELETE = "CALL `DELETE_PRODUCT`(?)";

        static final public String READ_ALL = "CALL `GET_PRODUCTS`()";
        static final public String READ_AVAILABLE = "CALL `GET_AVAILABLE_PRODUCTS`()";
        static final public String READ_RUNNING_OUT = "CALL `GET_RUNNING_OUT_PRODUCTS`()";
        static final public String READ_BY_ID = "CALL `GET_PRODUCT_BY_ID`(?)";

        static final public String GET_PRODUCT_QUANTITY_WITH_REGULAR_PRICE_AND_PRODUCT_ID = "CALL `GET_PRODUCT_QUANTITY_WITH_REGULAR_PRICE_AND_PRODUCT_ID`(?)";
        static final public String READ_TOP_10 = "CALL `GET_TOP_10_PRODUCTS`()";
    }
}
