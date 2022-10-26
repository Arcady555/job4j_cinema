package ru.job4j.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.persistence.TicketDbStore;
import ru.job4j.model.Ticket;
import java.util.List;
import java.util.Optional;

@Service
@ThreadSafe
public class TicketService {
    private final TicketDbStore ticketDbStore;

    public TicketService(TicketDbStore ticketDbStore) {
        this.ticketDbStore = ticketDbStore;
    }

    public List<Ticket> findAll() {
        return ticketDbStore.findAll();
    }

    public Optional<Ticket> add(Ticket ticket) {
        return ticketDbStore.add(ticket);
    }

    public Ticket findById(int id) {
        return ticketDbStore.findById(id);
    }

    public void delete(int id) {
        ticketDbStore.delete(id);
    }
}
