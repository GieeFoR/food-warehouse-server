package foodwarehouse.database.tables;

final public class Message {
    static final public String NAME = "MESSAGE";

    static final public class Columns {
        static final public String MESSAGE_ID = "MESSAGE_ID";
        static final public String SENDER_ID = "SENDER_ID";
        static final public String RECEIVER_ID = "RECEIVER_ID";
        static final public String CONTENT = "CONTENT";
        static final public String STATE = "MESSAGE_STATE";
        static final public String SEND_DATE = "SEND_DATE";
        static final public String READ_DATE = "READ_DATE";
    }
}
