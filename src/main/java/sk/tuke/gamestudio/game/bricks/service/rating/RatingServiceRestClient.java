package sk.tuke.gamestudio.game.bricks.service.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.game.bricks.entity.Rating;
import sk.tuke.gamestudio.game.bricks.service.GameStudioException;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RatingServiceRestClient implements RatingService{

    private final String url = "http://localhost:8080/api";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating) {
        restTemplate.postForEntity(url + "/rating", rating, Rating.class);
    }

    @Override
    public List<Rating> getTopRating(String game) {
       return Arrays.asList(Objects.requireNonNull(restTemplate.getForEntity(url + "/rating/" + game, Rating[].class).getBody()));
    }

    @Override
    public int getAverageRating(String game) {
        try{
            return restTemplate.getForEntity(url + "/rating/" + game, Integer.class).getBody();
        }
        catch (Exception e) {
            throw new UnsupportedOperationException("NULL");
        }
    }
    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
