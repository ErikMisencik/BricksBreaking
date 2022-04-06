package sk.tuke.gamestudio.game.bricks.core;

import java.io.Serializable;

public class Tile implements Serializable {

    private TileColor color = TileColor.NONE;

    public TileColor getColor() {
        return color;
    }

    void setColor(TileColor color) {
        this.color = color;
    }

}
