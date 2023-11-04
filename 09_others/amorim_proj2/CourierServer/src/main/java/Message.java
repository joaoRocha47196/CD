import java.util.List;

public class Message {
    // common attributes
    public MessageType type;
    public String requestID;

    // internal messages attributes
    public List<Request> pendingRequests;     // used for join response message type

    // external messages attributes
    public Request request;             // used for request message type

    public boolean isRequest() {
        return type == MessageType.REQUEST;
    }

    public boolean isJoinResponse() {
        return type == MessageType.JOIN_RESPONSE;
    }

    public boolean isLeader() {
        return type == MessageType.LEADER;
    }
}
