package sk.tuke.gamestudio.game.bricks.core;

import java.util.Random;

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

    //GENERATION OF FIELD
    public void generate() {
        fill();
        fillWithColors();
    }

    //FOR NOT HAVING TILES OF STATUS NULL
    private void fill(){
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                tiles[row][column] = new Tile();
            }
        }
    }

    //SETTING TILES RANDOM COLOR FROM TILECOLOR
    private void fillWithColors() {
        Random random = new Random();

        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                Tile tile = getTile(row, column);
                if (tile.getColor() == TileColor.NONE) {

                    int number = random.nextInt(3)+1;

                    switch (number) {
                        case 1 -> tiles[row][column].setColor(TileColor.RED);
                        case 2 -> tiles[row][column].setColor(TileColor.YELLOW);
                        case 3 -> tiles[row][column].setColor(TileColor.BLUE);
                    }

                }
            }
        }
    }

    //RESET OF FIELD
    public void resetField() {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                Tile tile = getTile(row, column);
                tile.setColor(TileColor.NONE);
            }
        }
        setRemovedTilesCount(0);
    }

    //REMOVING OF TILES FROM FIELD
    public  int removeTiles(int row, int column){

        //NEEDED CONDITIONS
        if(row <0 || column < 0 || row >= getRowCount() || column >= getColumnCount()){
            return 0;
        }
        if(getTile(row,column).getColor() != color){
            return 0;
        }

        // SETTING STATUS OF NONE TO COMPOTENT(MORE TILES WITH 1 COLOR TOGETHER)
        // CANT BE SEEN IN UI
        tiles[row][column].setColor(TileColor.NONE);
        removedTiles = 1;   //removedBricks

        //ALOGORITHM FOR FINDING A COMPONENT

        for (int r = row - 1; r <= row + 1; r++)
            for (int c = column - 1; c <= column + 1; c++)
                if (((r == row - 1 || r == row + 1) && c == column) || ((c == column - 1 || c == column + 1) && r == row))
                    removedTiles += removeTiles(r, c);
       return removedTiles;
    }

    //CHOOSEN TILE BY PLAYER
    public void chooseTile(int row, int column, Player player){
                if(getTile(row,column).getColor() != TileColor.NONE) {
                    //GETTING COLOR OF CHOSEN TILE
                    color = getTile(row,column).getColor();
                    removedTilesCount += removeTiles(row, column);
                    //REMOVING HP IF WAS ONLY 1 TILE IN 1 COMPONENT
                    if(removedTiles == 1){
                        int playerLives = player.getLives();
                        playerLives --;
                        player.setLives(playerLives);
                    }
                    //SETTING SCORE FOR PLAYER
                    playerScore(player);
                }
    }

    //CALCULATING SCORE
    private void playerScore(Player player){
        int score = player.getScore();
        score = score + removedTiles*87;
        player.setScore(score);
    }

    //ALGORTIHM FOR TILES IN THE AIR
    //CORRECTIONS OF TILES IN FIELD AFTER REMOVING TILES
    public void fieldCorrection() {
        int i = 10;
        while (i != 0) {
            for (int column = 0; column < columnCount; column++) {
                for (int row = 0; row < rowCount - 1; row++) {
                    if (getTile(row + 1, column).getColor() == TileColor.NONE && getTile(row, column).getColor() != TileColor.NONE) {
                        color = getTile(row, column).getColor();
                        getTile(row + 1, column).setColor(color);
                        getTile(row, column).setColor(TileColor.NONE);
                    }
                }
            }
            i--;
        }
    }

    //CORRECTIONS OF TILES IF COLUMS WAS EMPY
    private void columnsCorrection(int row, int column){
        //LEFT SIDE CORRECTION
        if(column < (columnCount/2)) {
            for (int r = row; r >= 0; r--) {                     //r = 4
                for (int c = column; c > 0; c--) {              //c = 3
                    if (getTile(r, c).getColor() == TileColor.NONE && getTile(r, c - 1).getColor() != TileColor.NONE) {
                        color = getTile(r, c - 1).getColor();
                        getTile(r, c).setColor(color);
                        getTile(r, c - 1).setColor(TileColor.NONE);
                    }
                }
            }
        }
        //RIGHT SIDE CORRECTION
        else {
            for (int r = row; r >= 0; r--) {
                for (int c = column; c < columnCount-1; c++) {
                    if (getTile(r, c).getColor() == TileColor.NONE && getTile(r, c + 1).getColor() != TileColor.NONE) {
                        color = getTile(r, c + 1).getColor();
                        getTile(r, c).setColor(color);
                        getTile(r, c + 1).setColor(TileColor.NONE);
                    }
                }
            }
        }
    }

    //CHECK IF THERE ARE EMPTY COLUMS
    public void checkColumns(){
        int x = 10;
        while (x != 0) {
            for (int column = 0; column < columnCount; column++) {
                int i = 0;
                for (int row = 0; row < rowCount; row++) {
                    if (getTile(row, column).getColor() == TileColor.NONE) {
                        i++;
                    }
                    if (i == 5) {
                        columnsCorrection(row, column);
                    }
                }
            }
            x--;
        }

    }

    //GAME STATE IF SOLVED
    public boolean isSolved() {
        return rowCount * columnCount == removedTilesCount;
    }

    //GETTERS AND SETTERS

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
