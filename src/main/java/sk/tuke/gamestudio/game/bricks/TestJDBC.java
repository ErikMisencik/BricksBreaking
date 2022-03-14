package sk.tuke.gamestudio.game.bricks;

import sk.tuke.gamestudio.game.bricks.entity.Score;
import sk.tuke.gamestudio.game.bricks.service.ScoreService;
import sk.tuke.gamestudio.game.bricks.service.ScoreServiceJDBC;

import java.util.Date;

public class TestJDBC {
    public static void main(String[] args) throws Exception{

        ScoreService service = new ScoreServiceJDBC();
        service.reset();
        service.addScore(new Score("Marek", "BricksBreaking", 2000, new Date()));
        service.addScore(new Score("Erik", "Lolecko", 3647, new Date()));
        service.addScore(new Score("Bedo", "Cancer", 2000, new Date()));
        service.addScore(new Score("Jan", "BricksBreaking", 222, new Date()));

        var scores = service.getTopScores("BricksBreaking");
        System.out.println(scores);

    }
}
