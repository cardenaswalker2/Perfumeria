package com.profumi.profumi.controllers;

import com.profumi.profumi.models.Perfume;
import com.profumi.profumi.repositories.PerfumeRepository;
import com.profumi.profumi.services.GroqService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final GroqService groqService;
    private final PerfumeRepository perfumeRepository;

    public ChatController(GroqService groqService, PerfumeRepository perfumeRepository) {
        this.groqService = groqService;
        this.perfumeRepository = perfumeRepository;
    }

    @GetMapping("/perfumes")
    public List<Perfume> obtenerTodos() {
        return perfumeRepository.findAll();
    }

    @PostMapping("/enviar")
    public Map<String, String> enviarMensaje(@RequestBody Map<String, String> payload) {
        String mensaje = payload.get("mensaje");
        String historial = payload.getOrDefault("historial", "");
        String perfil = payload.getOrDefault("perfil", "");
        
        List<Perfume> perfumes = perfumeRepository.findAll();
        
        // Filter catalog based on user profile gender preference to save tokens and prevent payload limit errors
        String genderPref = "";
        if (perfil.toLowerCase().contains("género preferido: masculino") || perfil.toLowerCase().contains("preferido: hombre")) {
            genderPref = "hombre";
        } else if (perfil.toLowerCase().contains("género preferido: femenino") || perfil.toLowerCase().contains("preferido: mujer")) {
            genderPref = "mujer";
        }
        
        final String targetGender = genderPref;
        
        StringBuilder catalogBuilder = new StringBuilder();
        int count = 0;
        for (Perfume p : perfumes) {
            // Filter by gender preference if specified
            if (!targetGender.isEmpty() && p.getGenero() != null) {
                String g = p.getGenero().toLowerCase();
                if (!g.equals(targetGender) && !g.equals("unisex") && !g.equals("uniséx")) {
                    continue;
                }
            }
            if (count >= 10) {
                break;
            }
            catalogBuilder.append(String.format("ID: %s | Nombre: %s | Marca: %s | Familia: %s | Género: %s | Concentración: %s | Precio: $%s COP | Duración: %s | Proyección: %s | Clima: %s | Ocasión: %s | Stock: %d | Notas: %s\n",
                p.getId(), p.getNombre(), p.getMarca(), 
                p.getFamiliaOlfativa() != null ? p.getFamiliaOlfativa() : "N/A", 
                p.getGenero() != null ? p.getGenero() : "Unisex", 
                p.getConcentracion() != null ? p.getConcentracion() : "N/A",
                p.getPrecio() != null ? String.format("%,.0f", p.getPrecio()) : "0", 
                p.getDuracion() != null ? p.getDuracion() : "N/A", 
                p.getProyeccion() != null ? p.getProyeccion() : "N/A", 
                p.getClimaRecomendado() != null ? p.getClimaRecomendado() : "N/A",
                p.getOcasion() != null ? p.getOcasion() : "N/A", 
                p.getStock() != null ? p.getStock() : 0,
                p.getNotasOlfativas() != null ? String.join(", ", p.getNotasOlfativas()) : "N/A"));
            count++;
        }
        
        String respuesta = groqService.chatear(mensaje, historial, catalogBuilder.toString(), perfil);
        
        return Map.of("respuesta", respuesta);
    }
}
