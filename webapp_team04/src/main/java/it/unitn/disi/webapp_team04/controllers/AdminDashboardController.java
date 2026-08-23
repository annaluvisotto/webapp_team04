package it.unitn.disi.webapp_team04.controllers;


import it.unitn.disi.webapp_team04.repositories.UserRepository;
import org.springframework.stereotype.Controller;

@Controller
public class AdminDashboardController {
    UserRepository userRepository;

    public AdminDashboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }




}
