package sk.tuke.gamestudio.game.bricks.service.score;

import sk.tuke.gamestudio.game.bricks.entity.Score;
import sk.tuke.gamestudio.game.bricks.service.score.ScoreService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional  //mnozina prikazov na databazu ktore maju tvorit celok
public class ScoreServiceJPA implements ScoreService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addScore(Score score) {
        entityManager.persist(score);
    }

    @Override   //list score ako zoznam objektov
    public List<Score> getTopScores(String game) {
        return entityManager.createQuery("select s from Score s where s.game = :game  order by s.points desc")
                .setParameter("game",game)
                .setMaxResults(10).getResultList();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("delete from score").executeUpdate();
    }
}
