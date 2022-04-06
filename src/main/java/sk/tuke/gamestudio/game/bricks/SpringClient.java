package sk.tuke.gamestudio.game.bricks;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.gamestudio.game.bricks.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.bricks.core.Field;
import sk.tuke.gamestudio.game.bricks.service.comment.CommentService;
import sk.tuke.gamestudio.game.bricks.service.comment.CommentServiceJPA;
import sk.tuke.gamestudio.game.bricks.service.rating.RatingService;
import sk.tuke.gamestudio.game.bricks.service.rating.RatingServiceJPA;
import sk.tuke.gamestudio.game.bricks.service.score.ScoreService;
import sk.tuke.gamestudio.game.bricks.service.score.ScoreServiceJPA;

@SpringBootApplication
@Configuration
public class SpringClient {

    public static void main(String[] args) {
        //kde ma hladat komponenty
        SpringApplication.run(SpringClient.class);
    }

    @Bean
    public CommandLineRunner runner(ConsoleUI ui) {
        return args -> ui.play();
    }

    @Bean
    public ConsoleUI consoleUI(Field field) {
        return new ConsoleUI(field);
    }

    @Bean
    public Field field() {
        return new Field(5, 9);
    }

    @Bean   //oznacenie pre score
    public ScoreService scoreService(){
        return new ScoreServiceJPA();
    }
    @Bean   //oznacenie pre komponent
    public CommentService commentService(){
        return new CommentServiceJPA();
    }
    @Bean   //oznacenie pre rating
    public RatingService ratingService(){
        return new RatingServiceJPA();
    }



}
