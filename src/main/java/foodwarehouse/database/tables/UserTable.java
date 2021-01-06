package foodwarehouse.database.tables;

final public class UserTable {
    static final public String NAME = "USER";

    static final public class Columns {
        static final public String USER_ID = "USER_ID";
        static final public String USERNAME = "USERNAME";
        static final public String PASSWORD = "PASSWORD";
        static final public String PERMISSION = "PERMISSION";
        static final public String EMAIL = "E_MAIL";
    }

    static final public class Procedures {
        static final public String INSERT = "CALL `INSERT_USER`(?,?,?,?,?)";

        static final public String UPDATE = "CALL `UPDATE_USER`(?,?,?,?,?)";

        static final public String READ_ALL = "CALL `GET_USERS`()";
        static final public String READ_BY_ID = "CALL `GET_USER_BY_ID`(?)";
        static final public String READ_BY_USERNAME = "CALL `GET_USER_BY_USERNAME`(?)";
        static final public String READ_BY_EMAIL = "CALL `GET_USER_BY_EMAIL`(?)";
    }
}
