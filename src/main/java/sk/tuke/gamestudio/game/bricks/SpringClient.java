package sk.tuke.gamestudio.game.bricks;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.game.bricks.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.bricks.core.Field;
import sk.tuke.gamestudio.game.bricks.service.comment.CommentService;
import sk.tuke.gamestudio.game.bricks.service.comment.CommentServiceJPA;
import sk.tuke.gamestudio.game.bricks.service.comment.CommentServiceRestClient;
import sk.tuke.gamestudio.game.bricks.service.rating.RatingService;
import sk.tuke.gamestudio.game.bricks.service.rating.RatingServiceJPA;
import sk.tuke.gamestudio.game.bricks.service.rating.RatingServiceRestClient;
import sk.tuke.gamestudio.game.bricks.service.score.ScoreService;
import sk.tuke.gamestudio.game.bricks.service.score.ScoreServiceJPA;
import sk.tuke.gamestudio.game.bricks.service.score.ScoreServiceRestClient;

@SpringBootApplication
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tuke.gamestudio.game.bricks.server.*"))
//nebude scanovat balik server

public class SpringClient {

    public static void main(String[] args) {
        //nech spring nespusta web
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
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
       // return new ScoreServiceJPA();
        return new ScoreServiceRestClient();
        }

    @Bean   //oznacenie pre komponent
    public CommentService commentService(){
       // return new CommentServiceJPA();
        return new CommentServiceRestClient();
    }


    @Bean   //oznacenie pre rating
    public RatingService ratingService(){
       // return new RatingServiceJPA();
        return new RatingServiceRestClient();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
