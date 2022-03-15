package sk.tuke.gamestudio.game.bricks.entity;

import java.util.Date;

public class Rating {

    private String player;

    private String game;

    private int rating;

    private Date ratedAt;

    public Rating(String player, String game, int rating, Date ratedAt) {
        this.player = player;
        this.game = game;
        this.rating = rating;
        this.ratedAt = ratedAt;
    }

    //GETTERS AND SETTERS

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getRatedAt() {
        return ratedAt;
    }

    public void setRatedAt(Date ratedAt) {
        this.ratedAt = ratedAt;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "player='" + player + '\'' +
                ", game='" + game + '\'' +
                ", rating=" + rating +
                ", ratedAt=" + ratedAt +
                '}';
    }
}
