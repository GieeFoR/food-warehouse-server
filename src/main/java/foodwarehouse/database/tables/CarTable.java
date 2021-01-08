package foodwarehouse.database.tables;

final public class CarTable {
    static final public String NAME = "CAR";

    static final public class Columns {
        static final public String CAR_ID = "CAR_ID";
        static final public String DRIVER_ID = "DRIVER_ID";
        static final public String BRAND = "BRAND";
        static final public String MODEL = "MODEL";
        static final public String PROD_YEAR = "YEAR_OF_PROD";
        static final public String REG_NO = "REGISTRATION_NO";
        static final public String INSURANCE = "INSURANCE_EXP";
        static final public String INSPECTION = "INSPECTION_EXP";
    }

    static final public class Procedures {
        static final public String INSERT = "CALL `INSERT_CAR`(?,?,?,?,?,?,?,?)";

        static final public String UPDATE = "CALL `UPDATE_CAR`(?,?,?,?,?,?,?,?)";

        static final public String DELETE = "CALL `DELETE_CAR`(?)";

        static final public String READ_ALL = "CALL `GET_CARS`()";
        static final public String READ_BY_ID = "CALL `GET_CAR_BY_ID`(?)";
    }
}
