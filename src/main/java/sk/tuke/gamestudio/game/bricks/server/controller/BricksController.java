package sk.tuke.gamestudio.game.bricks.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sk.tuke.gamestudio.game.bricks.core.Field;
import sk.tuke.gamestudio.game.bricks.core.Tile;


@Controller
@RequestMapping("/bricks")
public class BricksController {

    private Field field = new Field(5, 9);

    @RequestMapping
    public String bricks(){
        return "bricks";
    }

    @RequestMapping("/new")
    public String newGame() {
        field = new Field(5, 9);
        return "bricks";
    }

    public String getHtmlField() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table>\n");
        for (int row = 0; row < field.getRowCount(); row++) {
            sb.append("<tr>\n");
            for (int column = 0; column < field.getColumnCount(); column++) {
                Tile tile = field.getTile(row, column);
                sb.append("<td>\n");
                sb.append("<a href='/bricks?row=" + row + "&column=" + column + "'>\n");
                sb.append("<img src='../static/images/bricks/" + getImageName(tile) + ".png'>");
                sb.append("</a>\n");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");
        return sb.toString();
    }

    public String getState() {
        return field.getState().toString();
    }

    private String getImageName(Tile tile) {
        switch (tile.getColor()) {
            case RED:
                return "red";
            case YELLOW:
                return "yellow";
            case BLUE:
                return "blue";
            case NONE:
                return "none";
            default:
                throw new RuntimeException("Unexpected tile state");
        }
    }



}
