package workerapp.spread;

import java.io.Serializable;

public class ResumoInfo implements Serializable {
    private String exchangeName;
    private String productType;
    private String filename;

    public ResumoInfo(String exchangeName, String productType, String filename) {
        this.exchangeName = exchangeName;
        this.productType = productType;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getFileName() {
        return filename;
    }

    public void setFileName(String filename) {
        this.filename = filename;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
