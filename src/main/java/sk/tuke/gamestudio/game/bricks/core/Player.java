package sk.tuke.gamestudio.game.bricks.core;

public class Player {

    private String name;
    private int lives;
    private int score;


    public Player(String name, int lives, int score) {

        this.name = name;
        this.lives = lives;
        this.score = score;

    }

    public String getName() {
        return name;
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
