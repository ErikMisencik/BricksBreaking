package sk.tuke.gamestudio.game.bricks.service.rating;

import sk.tuke.gamestudio.game.bricks.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class RatingServiceJPA implements RatingService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) {
        entityManager.persist(rating);
    }

    @Override
    public List<Rating> getTopRating(String game) {
        return entityManager.createQuery("select r from Rating r where r.game = :game order by r.rating desc")
                .setParameter("game",game)
                .setMaxResults(1).getResultList();
    }

    @Override
    public int getAverageRating(String game) {
        return (int)Math.round((Double)this.entityManager.createQuery("SELECT AVG(r.rating) FROM Rating r WHERE r.game =: game").setParameter("game", game).getSingleResult());}

//    @Override
//    public int getRating(String game, String player) {
//        return entityManager.createQuery("select r from Rating r where r.game = :game AND r.player = :player order by r.rating desc")
//                .setParameter("game", game)
//                .setParameter("player", player)
//                .getFirstResult();
//    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("delete from rating").executeUpdate();
    }
}
