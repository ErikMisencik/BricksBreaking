package sk.tuke.gamestudio.game.bricks.consoleui;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.game.bricks.core.Field;
import sk.tuke.gamestudio.game.bricks.core.GameState;
import sk.tuke.gamestudio.game.bricks.core.Player;
import sk.tuke.gamestudio.game.bricks.core.Tile;
import sk.tuke.gamestudio.game.bricks.entity.Comment;
import sk.tuke.gamestudio.game.bricks.entity.Rating;
import sk.tuke.gamestudio.game.bricks.entity.Score;
import sk.tuke.gamestudio.game.bricks.service.comment.CommentService;
import sk.tuke.gamestudio.game.bricks.service.rating.RatingService;
import sk.tuke.gamestudio.game.bricks.service.score.ScoreService;

import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI {

    private static final Pattern COMMAND_PATTERN = Pattern.compile("([0-4])([0-8])");
    private static final Pattern RATING_PATTERN = Pattern.compile("([1-5])");

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001B[32m";

    private final Field field;
    private Player player;

    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private CommentService commentService;


    public ConsoleUI(Field field) {
        this.field = field;
    }

    //VISUALIZATION OF GAME, ITERACTIVE SIDE OF GAME, CONSOLE UI
    public void play() {

        System.out.print("Enter Player Name: ");
        String playerName = scanner.nextLine();
        player = new Player(playerName,5, 0);

        do {
            printPlayerStats();
            System.out.print("Removed Tiles: ");
            System.out.println(field.getRemovedTilesCount());
            printField();
            processInput();

            //Create new Field if its solved
            if(field.isSolved()){
                if(player.getLives()>0) {
                    System.out.print("Do you wanna play again Y/N: ");
                    String line = scanner.nextLine().toUpperCase();
                    System.out.print("\n");
                    if("Y".equals(line) || "Z".equals(line) ){
                        field.resetField();
                        field.generate();
                    }
                    else {
                        field.setState(GameState.WON);
                    }
                }
                //ADD
                //if solved but with 0 lives, player can get 1 life from developer
                //to try get more points, maybe can add
            }
            //IF 0 LIVES - Lost game
                if(player.getLives()==0){
                    field.setState(GameState.FAILED);
                }
            field.fieldCorrection();    // Check for correction of tiles position
            field.checkColumns();       // Check for empty colums
            field.fieldCorrection();    // Check for correction of tiles position
        } while (field.getState() == GameState.PLAYING);

        printPlayerStats();

        //GameState and his print
        if (field.getState() == GameState.FAILED) {
            System.out.println("\nGame Failed!");
        } else {
            System.out.println("\nGame Won!");
        }

        //OPTIONAL COMMENTnRATE OF GAME

        System.out.print("\nDo you wanna Rate and Comment The Game Y/N: ");
        String line = scanner.nextLine().toUpperCase();
        System.out.print("\n");
        if ("Y".equals(line)) {
            ratingGame();
            commentGame();
        }
        else if ("N".equals(line)){
            System.out.print("WHAT A SHAME \n");
        }
        else {
            System.out.print("\nMISSED RIGHT KEY Hahhhaa \n");
        }

        scoreService.addScore(new Score(player.getName(),"BricksBreaking", player.getScore(), new Date()));
        printTopScores();
        //ADD
        //do you wanna reset ScoreBoard ? with service.reset();
    }

    //PLAYER STATS PRINT
    private void printPlayerStats(){
        System.out.print("Player Name:   ");
        System.out.println(player.getName());
        System.out.print("Player Lives:  ");
        System.out.println(player.getLives());
        System.out.print("Player Score:  ");
        System.out.println(player.getScore());

    }

    //ALGORITHM PRINTING FIELD
    private void printField() {
        System.out.print(" ");
        for (int column = 0; column < field.getColumnCount(); column++) {
            System.out.print(" ");
            System.out.print(column );
        }
        System.out.println();

        for (int row = 0; row < field.getRowCount(); row++) {
            System.out.print(row);
            for (int column = 0; column < field.getColumnCount(); column++) {
                Tile tile = field.getTile(row, column);
                System.out.print(" ");
                //SETTING TILES MARKER AND COLOR OF MARKER
                switch (tile.getColor()) {
                    case NONE ->  System.out.print(ANSI_GREEN +' '+ ANSI_RESET);
                    case RED ->  System.out.print(ANSI_RED + 'R'+ ANSI_RESET);
                    case YELLOW ->   System.out.print(ANSI_YELLOW + 'Y' + ANSI_RESET);
                    case BLUE ->  System.out.print(ANSI_BLUE +'B'+ ANSI_RESET);
                }
            }
            System.out.println();
        }
    }

    //INPUT FOR CHOOSING TILE
    private void processInput () {
            //CHOSSING OF TILE IN GAME
            System.out.print("Enter command (X - exit, 11 - Choose Tile): ");
            String line = scanner.nextLine().toUpperCase();
            if ("X".equals(line)) {
                field.save();
                System.exit(0);
            }
            Matcher matcher = COMMAND_PATTERN.matcher(line);
            if (matcher.matches()) {
                int row = Integer.parseInt(matcher.group(1));
                int column = Integer.parseInt(matcher.group(2));
                field.chooseTile(row,column, player);
            } else {
                System.err.println("Wrong input " + line);
            }
        }

    //TABLE OF TOPSCORES
    private void printTopScores(){
        System.out.print("\nHall Of Fame 5: \n");
        var scores = scoreService.getTopScores("BricksBreaking");
        for(int i = 0; i< scores.size(); i++){
            var score = scores.get(i);
            System.out.printf("%d. %s %d\n", i+1, score.getPlayer(), score.getPoints());
        }
    }

    //RATE OF THE GAME
    private void ratingGame() {
        System.out.print("Enter Rating Of The Game (1-5): ");
        String rating = scanner.nextLine();
        Matcher rate = RATING_PATTERN.matcher(rating);
        if (rate.matches()) {
            int number = Integer.parseInt(rate.group(1));
            ratingService.setRating(new Rating(player.getName(), "BricksBreaking", number, new Date()));
        } else {
            System.err.print("Wrong input - Try Again: ");
            ratingGame();
        }
    }

    //COMMENT OF THE GAME
    private void commentGame(){
        System.out.print("Write Comment About The Game: ");
        String comment = scanner.nextLine();
        commentService.addComment(new Comment(player.getName(), "BricksBreaking", comment, new Date()));
    }

}
