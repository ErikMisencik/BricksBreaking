package sk.tuke.gamestudio.game.bricks.service;

import sk.tuke.gamestudio.game.bricks.entity.Rating;

import java.util.List;

public interface RatingService {

    void addRating(Rating rating);

    List<Rating> getTopRating(String game);

    void reset();
}
