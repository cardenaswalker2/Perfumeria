package com.profumi.profumi.controllers;

import com.profumi.profumi.services.GroqService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/diagnostico")
public class DiagnosticoApiController {

    private final GroqService groqService;
    private final com.profumi.profumi.repositories.PerfumeRepository perfumeRepository;

    public DiagnosticoApiController(GroqService groqService, com.profumi.profumi.repositories.PerfumeRepository perfumeRepository) {
        this.groqService = groqService;
        this.perfumeRepository = perfumeRepository;
    }

    @org.springframework.web.bind.annotation.GetMapping("/perfumes")
    public java.util.List<com.profumi.profumi.models.Perfume> obtenerTodos() {
        return perfumeRepository.findAll();
    }

    @PostMapping("/analizar")
    public String analizarPerfil(@RequestBody Map<String, String> respuestas) {
        return groqService.obtenerRecomendacion(respuestas);
    }
}
