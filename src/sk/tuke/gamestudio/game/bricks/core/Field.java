package sk.tuke.gamestudio.game.bricks.core;

import java.util.Random;

import static sk.tuke.gamestudio.game.bricks.core.TileColor.*;

public class Field {

    public GameState state = GameState.PLAYING;
    private final int rowCount;
    private final int columnCount;
    private final Tile[][] tiles;
    private TileColor color;
    private int removedTilesCount;
    private int removedTiles;


    public Field(int rowCount, int columnCount) {

        this.rowCount = rowCount;
        this.columnCount = columnCount;
        tiles = new Tile[rowCount][columnCount];
        generate();
    }

    public void generate() {
        fill();
        fillWithColors();
    }

    private void fill(){
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                tiles[row][column] = new Tile();
            }
        }
    }

    private void fillWithColors() {
        Random random = new Random();

        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                Tile tile = getTile(row, column);
                if (tile.getColor() == NONE) {

                    int number = random.nextInt(3)+1;

                    switch (number) {
                        case 1 -> tiles[row][column].setColor(RED);
                        case 2 -> tiles[row][column].setColor(YELLOW);
                        case 3 -> tiles[row][column].setColor(BLUE);
                    }

                }
            }
        }
    }

    public void resetField() {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                Tile tile = getTile(row, column);
                tile.setColor(NONE);
            }
        }
        setRemovedTilesCount(0);
    }

    public  int removeTiles(int row, int column){

        //nutne podmienky
        if(row <0 || column < 0 || row >= getRowCount() || column >= getColumnCount()){
            return 0;
        }

        if(getTile(row,column).getColor() != color){
            return 0;
        }

        // najdenie komponentu a zadanie jeho farby na 0, kvazi po odkliknuti
        tiles[row][column].setColor(NONE);
        removedTiles = 1;   //removedBricks

        //prehliadavanie

        for (int r = row - 1; r <= row + 1; r++)
            for (int c = column - 1; c <= column + 1; c++)
                if (((r == row - 1 || r == row + 1) && c == column) || ((c == column - 1 || c == column + 1) && r == row))
                    removedTiles += removeTiles(r, c);
       return removedTiles;
    }

    public void chooseTile(int row, int column, Player player){
                if(getTile(row,column).getColor() != NONE) {
                    color = getTile(row,column).getColor();           //urcime si vybranu farbu
                    removedTilesCount += removeTiles(row, column);
                    if(removedTiles == 1){
                        int playerLives = player.getLives();
                        playerLives --;
                        player.setLives(playerLives);
                    }
                    player.setScore((removedTilesCount*130)); // urob nejake rozne hodnoty
                }
    }

    public boolean isSolved() {
        return rowCount * columnCount == removedTilesCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public Tile getTile(int row, int column) {
        return tiles[row][column];
    }

    public int getRemovedTilesCount() {
        return removedTilesCount;
    }

    public void setRemovedTilesCount(int removedTilesCount) {
        this.removedTilesCount = removedTilesCount;
    }
}
