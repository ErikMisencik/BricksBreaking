package sk.tuke.gamestudio.game.bricks.service;

import sk.tuke.gamestudio.game.bricks.entity.Rating;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RatingServiceJDBC implements RatingService {

    public static final String JDBC_URL = "jdbc:postgresql://localhost:5432/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "erikmisencik";
    public static final String INSERT_STATEMENT = "INSERT INTO rating (player, game, rating, rated_at) VALUES (?, ?, ?, ?)";
    public static final String DELETE_STATEMENT = "DELETE FROM rating";
    public static final String SELECT_STATEMENT = "SELECT player, game, rating, rated_at FROM rating WHERE game = ? ORDER BY rating DESC LIMIT 10";


    @Override
    public void setRating(Rating rating) {
        try(var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement = connection.prepareStatement(INSERT_STATEMENT)
        ){
            statement.setString(1, rating.getPlayer());
            statement.setString(2, rating.getGame());
            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new Timestamp(rating.getRatedAt().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public List<Rating> getTopRating(String game) {
        try(var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement = connection.prepareStatement(SELECT_STATEMENT)
        ){
            statement.setString(1, game);
            try(var rs = statement.executeQuery()){
                var ratings = new ArrayList<Rating>();
                while (rs.next()){
                    ratings.add(new Rating(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4)));
                }
                return ratings;
            }
        }
        catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public int getAverageRating(String game) {

        try(var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement = connection.prepareStatement(SELECT_STATEMENT)
        ){
            int sum=0;
            statement.setString(1, game);
            try(var rates = statement.executeQuery()) {
                var ratings = new ArrayList<Rating>();
                while (rates.next()) {
                    ratings.add(new Rating(rates.getString(1), rates.getString(2), rates.getInt(3), rates.getTimestamp(4)));
                    sum += rates.getInt(3);
                }
                return sum/ratings.size();
            }
        }
        catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public int getRating(String game, String player) {

        try(var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement = connection.prepareStatement(SELECT_STATEMENT)
        ){
            statement.setString(1, game);
            statement.setString(2, player);
            try(var getRate = statement.executeQuery()){
                var rate = new Rating(getRate.getString(1), getRate.getString(2), getRate.getInt(3), getRate.getTimestamp(4));
                return rate.getRating();
            }
        }
        catch (SQLException e) {
            throw new GameStudioException(e);
        }
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
