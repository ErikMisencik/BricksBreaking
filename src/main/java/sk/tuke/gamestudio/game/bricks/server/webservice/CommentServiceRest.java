package sk.tuke.gamestudio.game.bricks.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.game.bricks.entity.Comment;
import sk.tuke.gamestudio.game.bricks.service.comment.CommentService;

import java.util.List;


@RestController
@RequestMapping("/api/comment")   //zmenit potom url veci podla mna
public class CommentServiceRest {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public void addComment(@RequestBody Comment comment) {
        commentService.addComment(comment);
    }

    @GetMapping("/{game}") //{} tieto zatvorky pretoze premenna game
    public List<Comment> getComments(@PathVariable String game) {
        return commentService.getComments(game);
    }
}
