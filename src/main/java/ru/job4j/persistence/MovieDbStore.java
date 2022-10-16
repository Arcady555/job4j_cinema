package ru.job4j.persistence;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@ThreadSafe
public class MovieDbStore {
    private static final Logger LOG = LogManager.getLogger(MovieDbStore.class.getName());
    private static final String FIND_ALL = "SELECT * FROM movies";
    private static final String ADD = "INSERT INTO movies(name) VALUES (?)";
    private static final String FIND_BY_ID = "SELECT * FROM movies WHERE id = ?";

    private final BasicDataSource pool;

    public MovieDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    Movie movie = sqlGetMovie(it);
                    movies.add(movie);
                }
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
        return movies;
    }

    public void add(Movie movie) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, movie.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    movie.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
    }

    public Movie findById(int id) {
        Movie movie = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    movie = sqlGetMovie(it);
                }
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
        return movie;
    }

    private Movie sqlGetMovie(ResultSet it) throws SQLException {
        return new Movie(
                it.getInt("id"),
                it.getString("name"));
    }
}