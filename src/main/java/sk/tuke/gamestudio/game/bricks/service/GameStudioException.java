package sk.tuke.gamestudio.game.bricks.service;

public class GameStudioException extends RuntimeException{

    public GameStudioException() {
    }

    public GameStudioException(String message) {
        super(message);
    }

    public GameStudioException(Throwable cause) {
        super(cause);
    }

    public GameStudioException(String message, Throwable cause) {
        super(message, cause);
    }
}
