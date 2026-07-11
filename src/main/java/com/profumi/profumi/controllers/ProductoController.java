package com.profumi.profumi.controllers;

import com.profumi.profumi.models.Perfume;
import com.profumi.profumi.repositories.PerfumeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductoController {

    private final PerfumeRepository perfumeRepository;

    public ProductoController(PerfumeRepository perfumeRepository) {
        this.perfumeRepository = perfumeRepository;
    }

    @GetMapping("/producto/{id}")
    public String verProducto(@PathVariable String id, Model model) {
        Perfume perfume = perfumeRepository.findById(id).orElse(null);
        if (perfume == null) {
            return "redirect:/catalogo";
        }
        
        model.addAttribute("perfume", perfume);
        model.addAttribute("title", perfume.getNombre() + " | Profumi");
        
        // Productos recomendados (misma marca o misma familia olfativa)
        java.util.List<Perfume> recomendados = perfumeRepository.findAll().stream()
                .filter(p -> p.getId() != null && !p.getId().equals(id))
                .filter(p -> (p.getMarca() != null && perfume.getMarca() != null && p.getMarca().equals(perfume.getMarca())) || 
                            (p.getNotasOlfativas() != null && perfume.getNotasOlfativas() != null && 
                             p.getNotasOlfativas().stream().anyMatch(perfume.getNotasOlfativas()::contains)))
                .limit(4)
                .toList();
        
        model.addAttribute("recomendados", recomendados);
        
        return "producto";
    }
}
