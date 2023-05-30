package sg.edu.nus.iss.ssf16_workshop.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.ssf16_workshop.model.Mastermind;
import sg.edu.nus.iss.ssf16_workshop.service.BoardGameService;

@RestController
@RequestMapping(path = "/api/boardgame",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardGameController {
    
    private BoardGameService service;

    public BoardGameController(BoardGameService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> createBoardGame(@RequestBody Mastermind m) {
        
        int insertCount = service.saveGame(m);
        Mastermind result = new Mastermind();

        result.setId(m.getId());
        result.setInsertCount(insertCount);
        System.out.println(m);
        System.out.println(result);

        if (insertCount == 0) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toJSONInsert().toString());
        }
        

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toJSONInsert().toString());
    }


    @GetMapping(path = "{mId}")
    public ResponseEntity<String> getBoardGame(@PathVariable String mId) throws IOException {

        Mastermind m = service.findById(mId);

        if (m == null || m.getName() == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(m.toJSON().toString());
    }
    
    @PutMapping(path = "{mId}")
    public ResponseEntity<String> updateBoardGame (
        @RequestBody Mastermind m,
        @PathVariable String mId,
        @RequestParam(defaultValue = "false") boolean isUpsert
    ) throws IOException {

        Mastermind result = service.findById(mId);
        if (!isUpsert) {
            result = service.findById(mId);
            if (result == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("");
            }
        }

        m.setId(mId);
        int updateCount = service.updateBoardGame(m);
        m.setUpdateCount(updateCount);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(null);
    }

}
