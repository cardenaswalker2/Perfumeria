package com.profumi.profumi.controllers;

import com.profumi.profumi.models.Perfume;
import com.profumi.profumi.repositories.PerfumeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CatalogoController {

    private final PerfumeRepository perfumeRepository;

    public CatalogoController(PerfumeRepository perfumeRepository) {
        this.perfumeRepository = perfumeRepository;
    }

    @GetMapping("/catalogo")
    public String verCatalogo(@RequestParam(required = false) String categoria, 
                             @RequestParam(required = false) String nota, 
                             @RequestParam(required = false) String genero, 
                             @RequestParam(required = false) String q, 
                             @RequestParam(required = false) Double minPrice,
                             @RequestParam(required = false) Double maxPrice,
                             @RequestParam(required = false) String sort,
                             Model model) {
        
        List<Perfume> perfumes = perfumeRepository.findAll();

        // 1. Filtrado Combinado
        perfumes = perfumes.stream()
                .filter(p -> (q == null || q.isEmpty() || 
                             (p.getNombre() != null && p.getNombre().toLowerCase().contains(q.toLowerCase())) || 
                             (p.getMarca() != null && p.getMarca().toLowerCase().contains(q.toLowerCase()))))
                .filter(p -> (genero == null || genero.isEmpty() || 
                             (p.getGenero() != null && p.getGenero().equalsIgnoreCase(genero))))
                .filter(p -> (categoria == null || categoria.isEmpty() || 
                             (p.getOcasion() != null && p.getOcasion().equalsIgnoreCase(categoria))))
                .filter(p -> (nota == null || nota.isEmpty() || 
                             (p.getNotasOlfativas() != null && p.getNotasOlfativas().stream().anyMatch(n -> n.equalsIgnoreCase(nota)))))
                .filter(p -> (minPrice == null || (p.getPrecio() != null && p.getPrecio() >= minPrice)))
                .filter(p -> (maxPrice == null || (p.getPrecio() != null && p.getPrecio() <= maxPrice)))
                .collect(java.util.stream.Collectors.toList());

        // 2. Ordenamiento
        if (sort != null && !sort.isEmpty()) {
            switch (sort) {
                case "price_asc":
                    perfumes.sort(java.util.Comparator.comparing(Perfume::getPrecio, java.util.Comparator.nullsLast(java.util.Comparator.naturalOrder())));
                    break;
                case "price_desc":
                    perfumes.sort(java.util.Comparator.comparing(Perfume::getPrecio, java.util.Comparator.nullsLast(java.util.Comparator.naturalOrder())).reversed());
                    break;
                case "name_asc":
                    perfumes.sort(java.util.Comparator.comparing(Perfume::getNombre, java.util.Comparator.nullsLast(java.util.Comparator.naturalOrder())));
                    break;
                case "newest":
                    // Asumiendo que el ID de MongoDB (ObjectId) tiene marca de tiempo, o simplemente reversa
                    java.util.Collections.reverse(perfumes);
                    break;
            }
        }
        
        model.addAttribute("perfumes", perfumes);
        model.addAttribute("title", "Catálogo | Profumi");
        return "catalogo";
    }
}
