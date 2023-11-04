public class Request {
    public String requestID;
    public String addressSrc;           // for the workers queue
    public String addressDest;          // used for request type message
    public String exchangeName;

    public Request(String requestID, String addressSrc, String addressDest, String exchangeName) {
        this.requestID = requestID;
        this.addressSrc = addressSrc;
        this.addressDest = addressDest;
        this.exchangeName = exchangeName;
    }
}