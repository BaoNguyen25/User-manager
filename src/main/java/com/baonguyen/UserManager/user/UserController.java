package com.baonguyen.UserManager.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        model.addAttribute("pageTitle", "Add New User");
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes ra) {
        //save user to database
        try {
            userService.save(user);
            ra.addFlashAttribute("addMessage", "The user has been saved successfully");
        }
        catch(IllegalStateException e) {
            ra.addFlashAttribute("emailUsed" , e.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/users/update/{id}")
    public String showUpdateForm(@PathVariable(name="id") Integer id, Model model, RedirectAttributes ra) throws IllegalStateException {
        try {
            //get user from service
            User user = userService.get(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Update User (ID: " + id + ")");
            return "user_form";
        }
        catch(IllegalStateException e) {
            ra.addFlashAttribute("updateMessage", e.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name="id") Integer id, RedirectAttributes ra) throws IllegalStateException {
        try {
            userService.delete(id);
            ra.addFlashAttribute("deleteMessage", "The user with ID " + id + " has been deleted");
        }
        catch(IllegalStateException e) {
            ra.addFlashAttribute("deleteMessage", e.getMessage());
        }
        return "redirect:/users";
    }

}
