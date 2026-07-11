package com.profumi.profumi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final com.profumi.profumi.repositories.PerfumeRepository perfumeRepository;

    public HomeController(com.profumi.profumi.repositories.PerfumeRepository perfumeRepository) {
        this.perfumeRepository = perfumeRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Profumi | Perfumería Premium Cartagena");
        model.addAttribute("featured", perfumeRepository.findAll().stream().limit(3).toList());
        return "home";
    }
}