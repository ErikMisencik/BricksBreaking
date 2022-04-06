package sk.tuke.gamestudio.game.bricks;

import sk.tuke.gamestudio.game.bricks.core.Field;
import sk.tuke.gamestudio.game.bricks.consoleui.ConsoleUI;

import java.util.Scanner;

public class Main {
        public static void main(String[] args) {

            Field field;
            Scanner scanner = new Scanner(System.in);
            System.out.print("If you wanna play last saved game enter S:  ");
            String line = scanner.nextLine().toUpperCase();

            if("S".equals(line)){
                try {
                    System.out.print("Saved Game Loaded");
                    field = Field.load();
                } catch (Exception e) {
                    e.printStackTrace();
                    field = new Field(5, 9);
                }
            }
            else {
                System.out.print("New Game Created");
                field = new Field(5, 9);
            }
            ConsoleUI ui = new ConsoleUI(field);
            ui.play();
        }
    }

