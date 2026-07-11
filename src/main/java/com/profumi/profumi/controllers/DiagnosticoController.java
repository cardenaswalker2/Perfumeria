package com.profumi.profumi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DiagnosticoController {

    private final com.profumi.profumi.repositories.PerfumeRepository perfumeRepository;

    public DiagnosticoController(com.profumi.profumi.repositories.PerfumeRepository perfumeRepository) {
        this.perfumeRepository = perfumeRepository;
    }

    @GetMapping("/diagnostico")
    public String verDiagnostico(Model model) {
        model.addAttribute("perfumes", perfumeRepository.findAll());
        model.addAttribute("title", "Asesor Inteligente de Perfumes | Profumi");
        return "diagnostico";
    }
}
