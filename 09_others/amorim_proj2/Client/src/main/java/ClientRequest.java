public class ClientRequest {

    private String addressSrc;
    private String addressDest;
    private String exchangeName;
    private String group;
    private String IDRequest;

    public ClientRequest(String addressSrc, String addressDest, String exchangeName, String group, String IDRequest){
        this.addressSrc = addressSrc;
        this.addressDest = addressDest;
        this.exchangeName = exchangeName;
        this.group = group;
        this.IDRequest = IDRequest;
    }

    public ClientRequest(){}

    public String getAddressSrc() {
        return addressSrc;
    }

    public void setAddressSrc(String addressSrc) {
        this.addressSrc = addressSrc;
    }

    public String getAddressDest() {
        return addressDest;
    }

    public void setAddressDest(String addressDest) {
        this.addressDest = addressDest;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getIDRequest() {
        return IDRequest;
    }

    public void setIDRequest(String IDRequest) {
        this.IDRequest = IDRequest;
    }

    @Override
    public String toString() {
        return "ClientRequest{" +
                "addressSrc='" + addressSrc + '\'' +
                ", addressDest='" + addressDest + '\'' +
                ", exchangeName='" + exchangeName + '\'' +
                ", group='" + group + '\'' +
                ", IDRequest='" + IDRequest + '\'' +
                '}';
    }
}
