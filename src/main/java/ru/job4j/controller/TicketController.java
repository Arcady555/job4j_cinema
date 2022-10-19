package ru.job4j.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Ticket;
import ru.job4j.service.MovieService;
import ru.job4j.service.TicketService;
import ru.job4j.utility.Utility;

import javax.servlet.http.HttpSession;

@Controller
@ThreadSafe
public class TicketController {
    private final MovieService movieService;
    private final TicketService ticketService;

    public TicketController(TicketService ticketService, MovieService movieService) {
        this.ticketService = ticketService;
        this.movieService = movieService;
    }

    @GetMapping("/ticket/{ticketId}")
    public String byeTicketGet(Model model, @PathVariable("ticketId") int id, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        Ticket ticket = ticketService.findById(id);
        model.addAttribute("ticket", ticket);
        model.addAttribute("movie", movieService.findById(ticket.getMovieId()));
        return "ticket";
    }

    @GetMapping("/byeSuccess")
    public String byeSuccessGet(Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        return "byeSuccess";
    }
}