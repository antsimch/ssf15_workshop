package sg.edu.nus.iss.ssf16_workshop.model;

import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Pegs { 

    private int totalCount;
    private List<Type> types;


    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    public List<Type> getTypes() {
        return types;
    }
    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public JsonObjectBuilder toJSON() {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        List<JsonObjectBuilder> listOfTypes = this.getTypes()
            .stream()
            .map(t -> t.toJSON())
            .toList();

        for (JsonObjectBuilder l : listOfTypes) {
            arrBuilder.add(l);
        }

        return Json.createObjectBuilder()
            .add("total_count", this.getTotalCount())
            .add("types", arrBuilder);
    }

    public static Pegs createJSON(JsonObject o) {
        Pegs p = new Pegs();
        List<Type> result = new LinkedList<>();
        JsonNumber totalCount = o.getJsonNumber("total_count");
        JsonArray types = o.getJsonArray("types");

        for (int i = 0; i < types.size(); i++) {
            JsonObject o2 = types.getJsonObject(i);
            Type t = Type.createJSON(o2);
            result.add(t);
        }

        p.setTotalCount(totalCount.intValue());
        p.setTypes(result);

        return p;
    }

}
