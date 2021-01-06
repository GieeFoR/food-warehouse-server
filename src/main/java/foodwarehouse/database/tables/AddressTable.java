package foodwarehouse.database.tables;

final public class AddressTable {
    static public final String NAME = "ADDRESS";

    static final public class Columns {
        static final public String ADDRESS_ID = "ADDRESS_ID";
        static final public String COUNTRY = "COUNTRY";
        static final public String TOWN = "TOWN";
        static final public String POSTAL_CODE = "POSTAL_CODE";
        static final public String BUILDING_NUMBER = "BUILDING_NO";
        static final public String STREET = "STREET";
        static final public String APARTMENT_NUMBER = "APARTMENT_NO";
    }

    static final public class Procedures {
        static final public String INSERT = "CALL `INSERT_ADDRESS`(?,?,?,?,?,?,?)";

        static final public String UPDATE = "CALL `UPDATE_ADDRESS`(?,?,?,?,?,?,?)";

        static final public String READ_ALL = "CALL `GET_ADDRESSES`()";
        static final public String READ_BY_ID = "CALL `GET_ADDRESS_BY_ID`(?)";
    }
}
