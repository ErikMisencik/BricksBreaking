package sk.tuke.gamestudio.game.bricks.service.comment;

import sk.tuke.gamestudio.game.bricks.entity.Comment;

import java.util.List;

public interface CommentService {

    void addComment(Comment comment);

    List<Comment> getCommentFromPlayer(String player);

    List<Comment> getComments(String game);

    void reset();
}
