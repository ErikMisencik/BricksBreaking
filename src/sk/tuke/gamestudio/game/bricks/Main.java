package sk.tuke.gamestudio.game.bricks;

import sk.tuke.gamestudio.game.bricks.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.bricks.core.Field;

    public class Main {
        public static void main(String[] args) {
            Field field = new Field(5, 9);
            ConsoleUI ui = new ConsoleUI(field);
            ui.play();
        }
    }

