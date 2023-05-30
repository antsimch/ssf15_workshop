package sg.edu.nus.iss.ssf16_workshop.repository;

import java.io.IOException;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.ssf16_workshop.model.Mastermind;

@Repository
public class BoardGameRepository {
    
    private RedisTemplate<String, String> template;

    public BoardGameRepository(RedisTemplate<String, String> template) {
        this.template = template;
    }

    public int saveGame(final Mastermind m) {

        template.opsForValue().set(m.getId(), m.toJSON().toString());
        String result = (String) template.opsForValue().get(m.getId());

        if (result != null) {
            return 1;
        }

        return 0;
    }

    public Mastermind findById(final String mId) throws IOException {
        Mastermind m = null;
        String jsonVal = (String) template.opsForValue().get(mId);

        if (jsonVal != null) {
            m = Mastermind.create(jsonVal);
        }

        return m;
    }

    public int updateBoardGame(final Mastermind m){
        String result = (String)template
                    .opsForValue().get(m.getId());
        if(m.isUpsert()){
            if(result != null){
                template.opsForValue().set(m.getId()
                                , m.toJSON().toString());
            }else{
                m.setId(m.generateId(8));
                template.opsForValue()
                            .setIfAbsent(m.getId()
                            ,m.toJSON().toString());
            }
                
        }else{
            if(result != null){
                template.opsForValue().set(m.getId()
                                , m.toJSON().toString());
            }
        }

        result = (String)template.opsForValue().get(m.getId());
        if(result != null)
            return 1;
        return 0;
    }
}
