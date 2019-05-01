package cs8524.project.pictureteller.controller;

import cs8524.project.pictureteller.domain.User;
import cs8524.project.pictureteller.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private static final String USER_FORM_URL = "user/userform";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}/show")
    public String showById(@PathVariable String id, Model model) {

        model.addAttribute("user", userService.findById(Long.valueOf(id)));

        return "image/showimage";
    }

    @GetMapping("/user/new")
    public String newUser(Model model) {

        model.addAttribute("user", new User());

        return USER_FORM_URL;
    }

    @PostMapping("user")
    public String saveOrUpdate(@ModelAttribute("user") User user) {

        User savedUser = userService.saveUser(user);

        return "redirect:/user/" + savedUser.getId() + "/image";
    }
}
