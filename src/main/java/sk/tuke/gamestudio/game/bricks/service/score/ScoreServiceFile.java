package sk.tuke.gamestudio.game.bricks.service.score;

import sk.tuke.gamestudio.game.bricks.entity.Score;
import sk.tuke.gamestudio.game.bricks.service.GameStudioException;
import sk.tuke.gamestudio.game.bricks.service.score.ScoreService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreServiceFile implements ScoreService {
    private static final String SCORE_FILE = "score.bin";   //File

    private List<Score> scores = new ArrayList<>();

    @Override   //prida do listu score
    public void addScore(Score score) {
        scores.add(score);
        save();
    }
    @Override   //vyprazdni list score
    public void reset() {
        scores = new ArrayList<>();
        save();
    }

    @Override
    public List<Score> getTopScores(String game) {
        load();     //nacitanie udajov zo suboru a nasledne zoradenie v liste podla hodnoty
        return scores.stream()
                .sorted((s1, s2) -> -Integer.compare(s1.getPoints(), s2.getPoints()))
                .limit(10)
                .collect(Collectors.toList());
    }

    //zoznam score v listu scores ulozí do suboru
    private void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SCORE_FILE))) {
            oos.writeObject(scores);                        //ulozi score do suboru
        } catch (IOException e) {                           //ioe vynimky
            throw new GameStudioException("Error saving score ", e);
        }
    }
    //zoznam score v listu scores nacita zo suboru
    private void load() {                                     //objekt ktorý nam pomaha citat
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SCORE_FILE))) {
            scores = (List<Score>) ois.readObject();
        } catch (Exception e) {
            throw new GameStudioException("Error loading score ", e);
        }
    }
}

