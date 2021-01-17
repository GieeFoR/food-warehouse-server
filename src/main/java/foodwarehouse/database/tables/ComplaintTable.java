package foodwarehouse.database.tables;

final public class ComplaintTable {
    static final public String NAME = "COMPLAINT";

    static final public class Columns {
        static final public String COMPLAINT_ID = "COMPLAINT_ID";
        static final public String ORDER_ID = "ORDER_ID";
        static final public String CONTENT = "CONTENT";
        static final public String SEND_DATE = "SEND_DATE";
        static final public String STATE = "COMPLAINT_STATE";
        static final public String DECISION = "DECISION";
        static final public String DECISION_DATE = "DECISION_DATE";
    }

    static final public class Procedures {
        static final public String INSERT = "CALL `INSERT_COMPLAINT`(?,?,?)";

        static final public String CANCEL = "CALL `COMPLAINT_CANCEL`(?)";
        static final public String UPDATE_STATE = "CALL `COMPLAINT_ADD_DECISION`(?,?,?)";

        static final public String READ_BY_ID = "CALL `GET_COMPLAINT_BY_ID`(?)";
        static final public String READ_ALL = "CALL `GET_COMPLAINTS`()";
        static final public String READ_BY_ORDER_ID = "CALL `GET_COMPLAINT_BY_ORDER_ID`(?)";
        static final public String READ_CUSTOMER_COMPLAINTS = "CALL `GET_CUSTOMER_COMPLAINTS`(?)";
    }
}
