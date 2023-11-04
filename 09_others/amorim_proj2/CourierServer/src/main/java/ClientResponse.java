public class ClientResponse {

    private final String boxID;
    private final String chave;
    private final String IDRequest;

    public ClientResponse(String boxID, String chave, String IDRequest){
        this.boxID = boxID;
        this.chave = chave;
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
