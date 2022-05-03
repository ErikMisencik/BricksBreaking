package sk.tuke.gamestudio.game.bricks.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.game.bricks.entity.Answer;

import java.util.Locale;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class GameController {

    private Answer answerr;

    @RequestMapping("/answer")
    public String answer(String answer) {

        answerr = new Answer(answer);
        if ("YES".equals(answer.toUpperCase(Locale.ROOT))) {
            return "redirect:/bricks/new";
        }
        else if("NO".equals(answer.toUpperCase(Locale.ROOT))){
            return "redirect:/end";
        }
        else{
            return "redirect:/answer/new";
        }
    }

    @RequestMapping("/answer/new")
    public String newAnswer() {
        return "answer";
    }
    @RequestMapping("/end")
    public String end() {
        return "end";
    }

    public boolean isAnswer() {
        return answerr != null;
    }
}
