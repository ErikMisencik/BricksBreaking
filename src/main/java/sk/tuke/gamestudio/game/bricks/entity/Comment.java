package sk.tuke.gamestudio.game.bricks.entity;

import java.util.Date;

public class Comment {

    private String player;

    private String game;

    private String comment;

    private Date writtenAt;

    public Comment(String player, String game, String comment, Date writtenAt) {
        this.player = player;
        this.game = game;
        this.comment = comment;
        this.writtenAt = writtenAt;
    }

    //GETERS AND SETTERS

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getWrittenAt() {
        return writtenAt;
    }

    public void setWrittenAt(Date writtenAt) {
        this.writtenAt = writtenAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "player='" + player + '\'' +
                ", game='" + game + '\'' +
                ", comment='" + comment + '\'' +
                ", writtenAt=" + writtenAt +
                '}';
    }
}
