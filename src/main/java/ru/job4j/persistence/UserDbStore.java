package ru.job4j.persistence;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.stereotype.Repository;
import ru.job4j.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Optional;

@Repository
@ThreadSafe
public class UserDbStore {
    private static final Logger LOG = LogManager.getLogger(TicketDbStore.class.getName());
    private static final String ADD = "INSERT INTO users(username, email, phone, password) VALUES (?, ?, ?, ?)";
    private static final String FIND = "SELECT * FROM users WHERE email = ? and password = ?;";

    private final BasicDataSource pool;

    public UserDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        Optional<User> rsl = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPassword());
            ps.execute();
            rsl = Optional.of(user);
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
        return rsl;
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        Optional<User> rsl = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    User user = new User(
                            it.getInt("id"),
                            it.getString("username"),
                            email,
                            it.getString("phone"),
                            password
                    );
                    rsl = Optional.of(user);
                }
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
        return rsl;
    }
}