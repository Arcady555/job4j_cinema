package ru.job4j.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.model.Ticket;
import ru.job4j.service.MovieService;
import ru.job4j.service.TheatreService;
import ru.job4j.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@ThreadSafe
public class TicketController {
    private final MovieService movieService;
    private final TicketService ticketService;
    private final TheatreService theatreService;

    public TicketController(TicketService ticketService, TheatreService theatreService, MovieService movieService) {
        this.ticketService = ticketService;
        this.theatreService = theatreService;
        this.movieService = movieService;
    }

    @GetMapping("/theatre/{id}")
    public String placeInTheatreGet(Model model, @PathVariable("id") int id, HttpSession httpSession) {
        model.addAttribute("idMovie", id);
        model.addAttribute("theatreRows", theatreService.getRows());
        model.addAttribute("theatreSeats", theatreService.getSeats());
        return "theatre";
    }

    @PostMapping("/theatre")
    public String placeInTheatrePost(HttpServletRequest req, @ModelAttribute Ticket ticket) {
        int id = Integer.parseInt(req.getParameter("idMovie"));
        ticket.setMovieId(id);
        ticketService.add(ticket);
        return "redirect:/ticket";
    }

    @GetMapping("/ticket")
    public String byeTicketPost(Model model, HttpSession httpSession) {
        model.addAttribute("ticket", ticketService.findById(60));
        return "ticket";
    }
}
