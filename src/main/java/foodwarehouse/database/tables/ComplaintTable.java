package foodwarehouse.database.tables;

public class ComplaintTable {
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
}
