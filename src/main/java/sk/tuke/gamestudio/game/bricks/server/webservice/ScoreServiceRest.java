package sk.tuke.gamestudio.game.bricks.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.game.bricks.entity.Score;
import sk.tuke.gamestudio.game.bricks.service.score.ScoreService;

import java.util.List;

@RestController
@RequestMapping("/api/score")   //zmenit potom url veci podla mna

public class ScoreServiceRest {

    @Autowired
    private ScoreService scoreService;

    @PostMapping
    public void addScore(@RequestBody Score score) {
        scoreService.addScore(score);
    }

    @GetMapping("/{game}") //{} tieto zatvorky pretoze premenna game
    public List<Score> getTopScores(@PathVariable String game) {
        return scoreService.getTopScores(game);
    }

}
