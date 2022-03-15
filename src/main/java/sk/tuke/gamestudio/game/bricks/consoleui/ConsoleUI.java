package sk.tuke.gamestudio.game.bricks.consoleui;

import sk.tuke.gamestudio.game.bricks.core.Field;
import sk.tuke.gamestudio.game.bricks.core.GameState;
import sk.tuke.gamestudio.game.bricks.core.Player;
import sk.tuke.gamestudio.game.bricks.core.Tile;
import sk.tuke.gamestudio.game.bricks.entity.Score;
import sk.tuke.gamestudio.game.bricks.service.ScoreService;
import sk.tuke.gamestudio.game.bricks.service.ScoreServiceJDBC;

import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI {

    private static final Pattern COMMAND_PATTERN = Pattern.compile("([0-4])([0-8])");

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001B[32m";

    private final Field field;
    private Player player;

    private final Scanner scanner = new Scanner(System.in);
    private final ScoreService scoreService = new ScoreServiceJDBC();

    public ConsoleUI(Field field) {
        this.field = field;
    }

    public void play() {
            //sprav, že do you wanna reset ScoreBoard ? pomocou  service.reset();
            //bud sprav reset na zaciatku alebo sa opytaj na konci hry
        System.out.print("Enter Player Name: ");
        String playerName = scanner.nextLine();
        player = new Player(playerName,5, 0);
        do {
            printPlayerStats();
            System.out.print("Removed Tiles: ");
            System.out.println(field.getRemovedTilesCount());
            printField();
            processInput();

            //Repeat new Field
            if(field.isSolved()){

                    //pridať možnosť hračovy ci chce pokračovať dalej
                System.out.print("Do you wanna play again Y/N: ");
                String line = scanner.nextLine().toUpperCase();
                if ("N".equals(line)) {
                    field.setState(GameState.WON);
                }

                    field.resetField();
                    field.generate();

            }
                if(player.getLives()==0){
                    field.setState(GameState.FAILED);
                }
            field.fieldCorrection();    // pridany check skontrolovat funkcionalitu
            field.checkColumns();       // uprava stlpcov prazdnych
            field.fieldCorrection();    // po uprave stlpcov ešte jedna uprava poľa

        } while (field.getState() == GameState.PLAYING);

        printPlayerStats();

        //pridanie do tabulky hracove udaje
        scoreService.addScore(new Score(playerName,"BricksBreaking", player.getScore(), new Date()));
        //printTopScores();

        if (field.getState() == GameState.FAILED) {
            System.out.println("\nGame Failed!");
        } else {
            System.out.println("\nGame Won!");
        }
    }

    private void printPlayerStats(){
        System.out.print("Player Name:   ");
        System.out.println(player.getName());
        System.out.print("Player Lives:  ");
        System.out.println(player.getLives());
        System.out.print("Player Score:  ");
        System.out.println(player.getScore());

    }

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
                switch (tile.getColor()) {
                    case NONE ->  System.out.print(ANSI_GREEN +'X'+ ANSI_RESET);
                    case RED ->  System.out.print(ANSI_RED + 'R'+ ANSI_RESET);
                    case YELLOW ->   System.out.print(ANSI_YELLOW + 'Y' + ANSI_RESET);
                    case BLUE ->  System.out.print(ANSI_BLUE +'B'+ ANSI_RESET);
                }
            }
            System.out.println();
        }
    }

    private void processInput () {
            System.out.print("Enter command (X - exit, 11 - Choose Tile): ");
            String line = scanner.nextLine().toUpperCase();
            if ("X".equals(line)) {
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
//    private void printTopScores(){
//        var scores = scoreService.getTopScores("BricksBreaking");
//        for(int i = 0; i< scores.size(); i++){
//            var score = scores.get(i);
//            System.out.printf("%d. %s %d\n", i, score.getPlayer(), score.getPoints());
//        }
//    }
}
