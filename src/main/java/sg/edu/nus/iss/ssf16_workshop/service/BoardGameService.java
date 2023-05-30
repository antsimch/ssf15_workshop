package sg.edu.nus.iss.ssf16_workshop.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import sg.edu.nus.iss.ssf16_workshop.model.Mastermind;
import sg.edu.nus.iss.ssf16_workshop.repository.BoardGameRepository;

@Service
public class BoardGameService {
    
    private BoardGameRepository repo;

    public BoardGameService(BoardGameRepository repo) {
        this.repo = repo;
    }

    public int saveGame(final Mastermind m) {
        return this.repo.saveGame(m);
    }

    public Mastermind findById(final String mId) throws IOException {
        return this.repo.findById(mId);
    }

    public int updateBoardGame(final Mastermind m){
        return this.repo.updateBoardGame(m);
    }
}
