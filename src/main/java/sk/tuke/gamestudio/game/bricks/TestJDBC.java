package sk.tuke.gamestudio.game.bricks;

import sk.tuke.gamestudio.game.bricks.entity.Comment;
import sk.tuke.gamestudio.game.bricks.entity.Rating;
import sk.tuke.gamestudio.game.bricks.entity.Score;
import sk.tuke.gamestudio.game.bricks.service.*;

import java.util.Date;

public class TestJDBC {
    public static void main(String[] args){

        ScoreService scoreService = new ScoreServiceJDBC();
        CommentService commentService = new CommentServiceJDBC();
        RatingService ratingService = new RatingServiceJDBC();

        scoreService.reset();
        commentService.reset();
        ratingService.reset();

        scoreService.addScore(new Score("Marek", "BricksBreaking", 2000, new Date()));
        scoreService.addScore(new Score("Erik", "Lolecko", 3647, new Date()));
        scoreService.addScore(new Score("Bedo", "Cancer", 2000, new Date()));
        scoreService.addScore(new Score("Jan", "BricksBreaking", 222, new Date()));

        commentService.addComment(new Comment("Bedo", "Cancer", "Lolecko milujem", new Date()));
        commentService.addComment(new Comment("Bedo", "Cancer", "Fakt toto su tlaky", new Date()));

        ratingService.addRating(new Rating("Jan", "BricksBreaking", 5, new Date()));
        ratingService.addRating(new Rating("Dano", "BricksBreaking", 2, new Date()));

        var scores = scoreService.getTopScores("BricksBreaking");
        System.out.println(scores);

        var comments = commentService.getCommentFrom("Bedo");
        System.out.println(comments);

        var ratings = ratingService.getTopRating("BricksBreaking");
        System.out.println(ratings);

    }
}
