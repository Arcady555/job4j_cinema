package ru.job4j.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.job4j.service.MovieService;
import ru.job4j.utility.Utility;

import javax.servlet.http.HttpSession;

@Controller
@ThreadSafe
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movieSelect")
    public String movieSelectGet(Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        model.addAttribute("movies", movieService.findAll());
        return "movieSelect";
    }
}

