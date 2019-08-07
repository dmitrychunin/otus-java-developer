package ru.otus.javadeveloper.hw13.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.javadeveloper.hw13.model.User;
import ru.otus.javadeveloper.hw13.service.DBService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ApiController {
    private final DBService<User> service;

    @GetMapping({"/", "/user/list"})
    public String userList(Model model) {
        List<User> users = service.getAll(User.class);
        model.addAttribute("users", users);
        return "userList.html";
    }

    @GetMapping("/user/create")
    public String userCreate(Model model) {
        model.addAttribute("user", new User());
        return "userCreate.html";
    }

    @PostMapping("/user/save")
    public RedirectView userSave(@ModelAttribute User user) {
        service.save(user);
        return new RedirectView("/user/list", true);
    }
}
