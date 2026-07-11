package com.profumi.profumi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExtraController {

    private final com.profumi.profumi.repositories.PerfumeRepository perfumeRepository;

    public ExtraController(com.profumi.profumi.repositories.PerfumeRepository perfumeRepository) {
        this.perfumeRepository = perfumeRepository;
    }

    @GetMapping("/kits")
    public String verKits(Model model) {
        java.util.List<com.profumi.profumi.models.Perfume> kits = perfumeRepository.findAll().stream()
                .filter(p -> p.getSku() != null && p.getSku().startsWith("KIT-"))
                .toList();
        
        model.addAttribute("kits", kits);
        model.addAttribute("title", "Kits de Descubrimiento | Profumi");
        return "kits";
    }

    @GetMapping("/buscar")
    public String buscar(Model model) {
        model.addAttribute("perfumes", perfumeRepository.findAll());
        model.addAttribute("title", "Buscar Perfumes | Profumi");
        return "buscar";
    }
}
