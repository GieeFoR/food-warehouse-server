package foodwarehouse.database.tables;

final public class EmployeeTable {
    static final public String NAME = "EMPLOYEE";

    static final public class Columns {
        static final public String EMPLOYEE_ID = "EMPLOYEE_ID";
        static final public String USER_ID = "USER_ID";
        static final public String NAME = "NAME";
        static final public String SURNAME = "SURNAME";
        static final public String POSITION = "POSITION";
        static final public String SALARY = "SALARY";
    }

    static final public class Procedures {
        static final public String INSERT = "CALL `INSERT_EMPLOYEE`(?,?,?,?,?,?)";

        static final public String UPDATE = "CALL `UPDATE_EMPLOYEE`(?,?,?,?,?)";

        static final public String DELETE = "CALL `DELETE_EMPLOYEE`(?)";

        static final public String READ_ALL = "CALL `GET_EMPLOYEES`()";
        static final public String READ_BY_ID = "CALL `GET_EMPLOYEE_BY_ID`(?)";
        static final public String READ_BY_USERNAME = "CALL `GET_EMPLOYEES_BY_USERNAME`(?)";

        static final public String FIND_WITH_MIN_DELIVERY = "CALL `GET_SUPPLIER_WITH_MIN_DELIVERIES`()";
    }
}
