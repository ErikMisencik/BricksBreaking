package sk.tuke.gamestudio.game.bricks.service;

import sk.tuke.gamestudio.game.bricks.entity.Comment;

import java.util.List;

public interface CommentService {

    void addComment(Comment comment);

    List<Comment> getCommentFrom(String player);

    void reset();
}
