package foodwarehouse.database.tables;

final public class MakerTable {
    static final public String NAME = "MAKER";

    static final public class Columns {
        static final public String MAKER_ID = "MAKER_ID";
        static final public String ADDRESS_ID = "ADDRESS_ID";
        static final public String FIRM_NAME = "FIRM_NAME";
        static final public String TELEPHONE_NO = "TELEPHONE_NO";
        static final public String E_MAIL = "E_MAIL";
    }

    static final public class Procedures {
        static final public String INSERT = "CALL `INSERT_MAKER`(?,?,?,?,?)";

        static final public String UPDATE = "CALL `UPDATE_MAKER`(?,?,?,?)";

        static final public String DELETE = "CALL `DELETE_MAKER`(?)";

        static final public String READ_ALL = "CALL `GET_MAKERS`()";
        static final public String READ_BY_ID = "CALL `GET_MAKER_BY_ID`(?)";
    }
}
