package workerapp.spread;

import com.google.gson.Gson;

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

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
