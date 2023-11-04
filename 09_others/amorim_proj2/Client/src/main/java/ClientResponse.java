public class ClientResponse {

    private String boxID;
    private String chave;
    private String IDRequest;

    public ClientResponse(String boxID, String chave, String IDRequest){
        this.boxID = boxID;
        this.chave = chave;
        this.IDRequest = IDRequest;
    }

    public String getBoxID() {
        return boxID;
    }

    public void setBoxID(String boxID) {
        this.boxID = boxID;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getIDRequest() {
        return IDRequest;
    }

    public void setIDRequest(String IDRequest) {
        this.IDRequest = IDRequest;
    }

    @Override
    public String toString() {
        return "ClientResponse{" +
                "boxID='" + boxID + '\'' +
                ", chave='" + chave + '\'' +
                ", IDRequest='" + IDRequest + '\'' +
                '}';
    }
}
