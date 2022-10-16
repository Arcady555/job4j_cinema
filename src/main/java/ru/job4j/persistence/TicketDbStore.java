package ru.job4j.persistence;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@ThreadSafe
public class TicketDbStore {
    private static final Logger LOG = LogManager.getLogger(TicketDbStore.class.getName());
    private static final String FIND_ALL = "SELECT * FROM tickets";
    private static final String ADD = "INSERT INTO tickets(movie_id, pos_row, cell, user_id) VALUES (?, ?, ?, '1')";
    private static final String FIND_BY_ID = "SELECT * FROM tickets WHERE id = ?";
    private static final String DELETE = "DELETE FROM tickets WHERE id = ?";

    private final BasicDataSource pool;

    public TicketDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    Ticket ticket = sqlGetTicket(it);
                    tickets.add(ticket);
                }
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
        return tickets;
    }

    public void add(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getMovieId());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getCell());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
    }

    public Ticket findById(int id) {
        Ticket ticket = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    ticket = sqlGetTicket(it);
                }
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
        return ticket;
    }

    public void delete(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(DELETE)
        ) {
            ps.setInt(1, id);
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
    }

    private Ticket sqlGetTicket(ResultSet it) throws SQLException {
        return new Ticket(
                it.getInt("id"),
                it.getInt("movie_id"),
                it.getInt("pos_row"),
                it.getInt("cell")
                //     it.getInt("user_id")
        );
    }
}
