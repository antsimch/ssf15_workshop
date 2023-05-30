package sg.edu.nus.iss.ssf16_workshop.model;

import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Pieces implements Serializable {
    
    private DecodingBoard decoding_board;
    private Pegs pegs;
    private Rulebook rulebook;

    public DecodingBoard getDecoding_board() {
        return decoding_board;
    }

    public void setDecoding_board(DecodingBoard decoding_board) {
        this.decoding_board = decoding_board;
    }

    public Pegs getPegs() {
        return pegs;
    }

    public void setPegs(Pegs pegs) {
        this.pegs = pegs;
    }

    public Rulebook getRulebook() {
        return rulebook;
    }

    public void setRulebook(Rulebook rulebook) {
        this.rulebook = rulebook;
    }

    public JsonObjectBuilder toJSON() {
        return Json.createObjectBuilder()
            .add("decoding_board", this.getDecoding_board().toJSON())
            .add("pegs", this.getPegs().toJSON())
            .add("rulebook", this.getRulebook().toJSON());
    }

    public static Pieces createJSON(JsonObject o) {
        Pieces p = new Pieces();
        JsonObject decoding_board = o.getJsonObject("decoding_board");
        JsonObject pegs = o.getJsonObject("pegs");
        JsonObject rulebook = o.getJsonObject("rulebook");
        p.setDecoding_board(DecodingBoard.createJSON(decoding_board));
        p.setPegs(Pegs.createJSON(pegs));
        p.setRulebook(Rulebook.createJSON(rulebook));
        return p;
    }
}
