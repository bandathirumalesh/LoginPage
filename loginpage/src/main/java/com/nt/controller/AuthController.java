package com.nt.controller;

import com.nt.model.User;
import com.nt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    
    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/login"; // redirect to login page
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login"; // shows login.html
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute User user, Model model) {
        User dbUser = userRepository.findByUsername(user.getUsername());
        if (dbUser != null && dbUser.getPassword().equals(user.getPassword())) {
            model.addAttribute("username", dbUser.getUsername());
            return "welcome"; // login successful
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/login"; // after register, go to login
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
