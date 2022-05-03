package sk.tuke.gamestudio.game.bricks.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sk.tuke.gamestudio.game.bricks.core.Field;
import sk.tuke.gamestudio.game.bricks.core.Player;
import sk.tuke.gamestudio.game.bricks.core.Tile;
import sk.tuke.gamestudio.game.bricks.entity.*;
import sk.tuke.gamestudio.game.bricks.service.comment.CommentService;
import sk.tuke.gamestudio.game.bricks.service.rating.RatingService;
import sk.tuke.gamestudio.game.bricks.service.score.ScoreService;

import java.util.Date;


@Controller

@Scope(WebApplicationContext.SCOPE_SESSION)
public class BricksController {

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;

    private Player player;
    private String login;


    private Field field = new Field(5, 9);
    private int start = 0;

    @RequestMapping("/bricks")
    public String bricks(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column, Model model){

        if(start ==0){
            field.fillWithColors();
            start++;
        }

        if(row != null && column != null){

            field.chooseTile(row,column, player);
            field.fieldCorrection();    // Check for correction of tiles position
            field.checkColumns();       // Check for empty colums
            field.fieldCorrection();    // Check for correction of tiles position
        }
        showModels(model);

        //PlayAgain
        if(player.getLives()==0){
            scoreService.addScore(new Score(player.getName(),"BricksBreaking", player.getScore(), new Date()));
            return "answer";
        }
        //Replay If Player Have Lives
        if(field.getRemovedTilesCount()==45){
            field = new Field(5, 9);
            field.fillWithColors();
            showModels(model);
        }
        return "bricks";
    }

    public String showScore(Model model){
        model.addAttribute("scores",scoreService.getTopScores("BricksBreaking"));
        return "bricks";
    }
    public String showComments(Model model){
        model.addAttribute("comments", commentService.getComments("BricksBreaking"));
        return "comments";
    }
    public String showRating(Model model){                     //xxxxxxxx
        model.addAttribute("rating", ratingService.getRating("BricksBreaking", this.login));
        return "rating";
    }

    public String showPlayer(Model model){
        model.addAttribute("player", player);
        return "bricks";
    }
    public String showRemovedTilesCount(Model model){
        model.addAttribute("field", field);
        return "bricks";
    }

    @RequestMapping("/bricks/new")
    public String newGame(Model model) {
        player = new Player(this.login, 5, 0);
        field = new Field(5, 9);
        field.fillWithColors();
        showModels(model);
        return "bricks";
    }

    public void showModels(Model model){
        showScore(model);
        showPlayer(model);
        showRemovedTilesCount(model);
    }

    public String getHtmlField() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table>\n");
        for (int row = 0; row < field.getRowCount(); row++) {
            sb.append("<tr>\n");
            for (int column = 0; column < field.getColumnCount(); column++) {
                Tile tile = field.getTile(row, column);
                sb.append("<td>\n");
                sb.append("<a href='/bricks?row=" + row + "&column=" + column + "'>\n");    //link
            //    sb.append("<img src ='/images/bricks/" + getImageName(tile) + ".png'>");
                sb.append("<" + getImageColor(tile) + ">\n");
                sb.append(getImageName(tile));
                sb.append("</" + getImageColor(tile) + ">\n");
                sb.append("</a>\n");                                                        //koniec linku
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");
        return sb.toString();
    }

    private String getImageName(Tile tile) {
        switch (tile.getColor()) {
            case RED:
                return "R";
            case YELLOW:
                return "Y";
            case BLUE:
                return "B";
            case NONE:
                return "_";
            default:
                throw new RuntimeException("Unexpected tile state");
        }
    }

    private String getImageColor(Tile tile) {
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

    //Button Action

    private String button() {
      return "bricks";
    }


    //user controller

    private User loggedUser;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login(String login, String password) {
        if ("heslo".equals(password)) {
            loggedUser = new User(login);
            this.login = login;
            player = new Player(this.login, 5, 0);
            return "redirect:/bricks";
        }

        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout() {
        loggedUser = null;
        return "redirect:/";
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public boolean isLogged() {
        return loggedUser != null;
    }

    //Comment controller

    private Comment komentik;

    @RequestMapping("/comments")
    public String comments(String comment,Model model) {
        showComments(model);
        comment=null;
        return "comments";
    }
    @RequestMapping("/comments/new")
    public String newComment(String comment, Model model) {
        if(comment!=null) {
            commentService.addComment(new Comment(this.login, "BricksBreaking", comment, new Date()));
        }
        showComments(model);
        return "comments";
    }

    //Rating controller


    @RequestMapping("/rating")
    public String rating(Model model) {
        showRating(model);
        return "rating";
    }
    @RequestMapping("/rating/new")
    public String newRating(Model model, int rating) {
            ratingService.setRating(new Rating(this.login, "BricksBreaking", rating, new Date()));
        showRating(model);
        return "rating";
    }
}
