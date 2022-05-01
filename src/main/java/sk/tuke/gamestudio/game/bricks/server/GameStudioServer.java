package sk.tuke.gamestudio.game.bricks.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import sk.tuke.gamestudio.game.bricks.service.comment.CommentService;
import sk.tuke.gamestudio.game.bricks.service.comment.CommentServiceJPA;
import sk.tuke.gamestudio.game.bricks.service.rating.RatingService;
import sk.tuke.gamestudio.game.bricks.service.rating.RatingServiceJPA;
import sk.tuke.gamestudio.game.bricks.service.score.ScoreService;
import sk.tuke.gamestudio.game.bricks.service.score.ScoreServiceJPA;

@SpringBootApplication
@Configuration
@EntityScan(basePackages = "sk.tuke.gamestudio.game.bricks.entity")
//@ComponentScan(basePackages = "sk.tuke.gamestudio.game.bricks.server.webservice")

public class GameStudioServer {
    public static void main(String[] args) {
        SpringApplication.run(GameStudioServer.class);  //kde ma hladat komponenty
    }

    @Bean   //oznacenie pre score
    public ScoreService scoreService(){
        return new ScoreServiceJPA();
    }
    @Bean   //oznacenie pre komponent comment
    public CommentService commentService(){
        return new CommentServiceJPA();
    }
    @Bean   //oznacenie pre rating
    public RatingService ratingService(){
        return new RatingServiceJPA();
    }

}
