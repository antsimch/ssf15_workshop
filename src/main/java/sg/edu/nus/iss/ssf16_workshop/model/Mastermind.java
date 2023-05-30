package sg.edu.nus.iss.ssf16_workshop.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.SecureRandom;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Mastermind implements Serializable {
    
    private String name;
    private Pieces pieces;
    private String id;
    private int insertCount;
    private int updateCount;
    private boolean isUpsert;

    public Mastermind() {
        this.id = generateId(8);
    }

    public synchronized String generateId(int maxChars) {

        SecureRandom sr = new SecureRandom();
        StringBuilder strBuilder = new StringBuilder();

        while (strBuilder.length() < maxChars) {
            strBuilder.append(Integer.toHexString(sr.nextInt()));
        }

        return strBuilder.toString().substring(0, maxChars);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pieces getPieces() {
        return pieces;
    }

    public void setPieces(Pieces pieces) {
        this.pieces = pieces;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getInsertCount() {
        return insertCount;
    }

    public void setInsertCount(int insertCount) {
        this.insertCount = insertCount;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }

    public boolean isUpsert() {
        return isUpsert;
    }

    public void setUpsert(boolean isUpsert) {
        this.isUpsert = isUpsert;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
            .add("name", this.getName())
            .add("pieces", this.getPieces().toJSON())
            .add("id", this.getId())
            .build();
    }

    public JsonObject toJSONInsert(){
        return Json.createObjectBuilder()
            .add("id", this.getId())
            .add("insert count", this.getInsertCount())
            .build();
    }

    public JsonObject toJSONUpdate() {
        return Json.createObjectBuilder()
            .add("id", this.getId())
            .add("update count", this.getUpdateCount())
            .build();
    }

    public static Mastermind create(String json) {
        Mastermind m = new Mastermind();
        if (json != null) {
            try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
                JsonReader r = Json.createReader(is);
                JsonObject j = r.readObject();
                m.setName(j.getString("name"));
                m.setPieces(Pieces.createJSON(j.getJsonObject("pieces")));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return m;
    }

    @Override
    public String toString() {
        return this.getId() + " " +this.getName();
    }
    
}
