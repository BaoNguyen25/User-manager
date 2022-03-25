package com.baonguyen.UserManager.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    final private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String showUserList(Model model) {
        // list all users
        List<User> listUsers = (List<User>) userService.listAll();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @GetMapping("/users/new")
    public String showNewForm(Model model) { // create model to bind data form
        model.addAttribute("user", new User());
        return "add_user";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes ra) {
        //save user to database
        userService.save(user);
        ra.addFlashAttribute("message", "The user has been added successfully");
        return "redirect:/users";
    }



}
