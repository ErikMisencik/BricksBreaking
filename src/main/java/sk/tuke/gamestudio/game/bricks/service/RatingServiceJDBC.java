package sk.tuke.gamestudio.game.bricks.service;

import sk.tuke.gamestudio.game.bricks.entity.Rating;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class RatingServiceJDBC implements RatingService {

    public static final String JDBC_URL = "jdbc:postgresql://localhost:5432/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "erikmisencik";
    //public static final String INSERT_STATEMENT = "INSERT INTO score (player, game, rating, ratedAt) VALUES (?, ?, ?, ?)";
    public static final String DELETE_STATEMENT = "DELETE FROM rating";
   // public static final String SELECT_STATEMENT = "SELECT player, game, points, played_at FROM score WHERE game = ? ORDER BY points DESC LIMIT 10";


    @Override
    public void addRating(Rating rating) {

    }

    @Override
    public List<Rating> getTopRating(String game) {
        return null;
    }

    @Override
    public void reset() {
        try(var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement = connection.createStatement()
        ){
            statement.executeUpdate(DELETE_STATEMENT);
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }

    }
}
