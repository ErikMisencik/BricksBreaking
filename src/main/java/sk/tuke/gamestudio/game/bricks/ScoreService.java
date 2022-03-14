package sk.tuke.gamestudio.game.bricks;

import sk.tuke.gamestudio.game.bricks.entity.Score;

import java.util.List;

public interface ScoreService {

    void addScore(Score score);

    List<Score> getTopScores(String game);

    void reset();
}
