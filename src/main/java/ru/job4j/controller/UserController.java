package ru.job4j.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.service.UserService;
import ru.job4j.model.User;
import ru.job4j.utility.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@ThreadSafe
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String addUserGet(Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        model.addAttribute("user",
                new User(0, "Заполните поле", "Заполните поле",
                        "Заполните поле", "Заполните поле"));
        return "addUser";
    }

    @PostMapping("/registration")
    public String addUserPost(@ModelAttribute User user) {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            return "redirect:/fail";
        }
        return "redirect:/userSuccess";
    }

    @GetMapping("/userSuccess")
    public String success(Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        return "printSuccess";
    }

    @GetMapping("/fail")
    public String fail(Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        return "printFail";
    }

    @GetMapping("/loginPage")
    public String loginGet(Model model, @RequestParam(name = "fail",
            required = false) Boolean fail, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/movieSelect";
    }

    @GetMapping("/loginout")
    public String loginOut(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/loginPage";
    }
}