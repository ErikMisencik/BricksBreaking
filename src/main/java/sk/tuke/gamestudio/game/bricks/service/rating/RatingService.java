package sk.tuke.gamestudio.game.bricks.service.rating;

import sk.tuke.gamestudio.game.bricks.entity.Rating;

import java.util.List;

public interface RatingService {

    Object setRating(Rating rating);

    List<Rating> getTopRating(String game);

    int getAverageRating(String game);

   int getRating(String game, String player);

    void reset();
}
