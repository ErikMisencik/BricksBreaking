package sk.tuke.gamestudio.game.bricks.service.comment;

import sk.tuke.gamestudio.game.bricks.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getCommentFromPlayer(String player) {
        return entityManager.createQuery("select c from Comment c where c.player = :player order by c.writtenAt desc")
                .setParameter("player",player)
                .getResultList();
    }

    @Override
    public List<Comment> getComments(String game) {
        return entityManager.createQuery("select c from Comment c where c.game = :game order by c.writtenAt desc")
                .setParameter("game",game)
                .getResultList();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("delete from comment").executeUpdate();
    }
}
