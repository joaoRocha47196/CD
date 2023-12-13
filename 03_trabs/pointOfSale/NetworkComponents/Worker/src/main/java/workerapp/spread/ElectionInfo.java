package workerapp.spread;

import java.io.Serializable;

public class ElectionInfo implements Serializable {
    private int id;

    public ElectionInfo(int maxId) {
        this.id = maxId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
