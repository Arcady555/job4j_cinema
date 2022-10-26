package ru.job4j.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Ticket;
import ru.job4j.model.User;
import ru.job4j.service.TheatreService;
import ru.job4j.service.TicketService;
import ru.job4j.utility.Utility;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@ThreadSafe
public class TheatreController {
    private final TicketService ticketService;
    private final TheatreService theatreService;

    public TheatreController(TicketService ticketService, TheatreService theatreService) {
        this.ticketService = ticketService;
        this.theatreService = theatreService;
    }

    @GetMapping("/theatre/{movieId}")
    public String placeInTheatreGet(Model model, @PathVariable("movieId") int id, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        model.addAttribute("idMovie", id);
        model.addAttribute("theatreRows", theatreService.getRows());
        model.addAttribute("theatreSeats", theatreService.getSeats());
        model.addAttribute("ticket", new Ticket(0, id, 0, 0, 0));
        return "theatre";
    }

    @PostMapping("/theatre")
    public String placeInTheatrePost(@ModelAttribute Ticket ticket) {
        Optional<Ticket> ticketDb = ticketService.add(ticket);
        if (ticketDb.isEmpty()) {
            return "redirect:/failTicket";
        }
        int ticketId = ticket.getId();
        return "redirect:/ticket/" + ticketId;
    }

    @GetMapping("/failTicket")
    public String fail(Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        return "printFailTicket";
    }
}
