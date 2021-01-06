package foodwarehouse.database.tables;

final public class StorageTable {
    static final public String NAME = "STORAGE";

    static final public class Columns {
        static final public String STORAGE_ID = "STORAGE_ID";
        static final public String ADDRESS_ID = "ADDRESS_ID";
        static final public String MANAGER_ID = "MANAGER_ID";
        static final public String STORAGE_NAME = "STORAGE_NAME";
        static final public String CAPACITY = "CAPACITY";
        static final public String IS_COLD = "IS_COLD_STORAGE";
    }

    static final public class Procedures {
        static final public String INSERT = "CALL `INSERT_STORAGE`(?,?,?,?,?,?)";

        static final public String UPDATE = "CALL `UPDATE_STORAGE`(?,?,?,?)";

        static final public String DELETE = "CALL `DELETE_STORAGE`(?)";

        static final public String READ_ALL = "CALL `GET_STORAGES`()";
        static final public String READ_BY_ID = "CALL `GET_STORAGE_BY_ID`(?)";
    }
}