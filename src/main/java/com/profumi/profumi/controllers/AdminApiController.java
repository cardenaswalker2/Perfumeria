package com.profumi.profumi.controllers;

import com.profumi.profumi.models.Perfume;
import com.profumi.profumi.repositories.PerfumeRepository;
import com.profumi.profumi.services.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminApiController {

    private final PerfumeRepository perfumeRepository;
    private final FileService fileService;
    
    // In-memory mock audits and builder sections for instantaneous Figma-like response times
    private static final List<Map<String, Object>> auditLogs = new ArrayList<>();
    private static List<Map<String, Object>> pageSections = new ArrayList<>();

    static {
        // Default sections
        Map<String, Object> s1 = new HashMap<>();
        s1.put("id", "sec-1");
        s1.put("type", "banner");
        s1.put("name", "Banner Héroe");
        s1.put("visible", true);
        pageSections.add(s1);

        Map<String, Object> s2 = new HashMap<>();
        s2.put("id", "sec-2");
        s2.put("type", "featured");
        s2.put("name", "Productos Destacados");
        s2.put("visible", true);
        pageSections.add(s2);

        Map<String, Object> s3 = new HashMap<>();
        s3.put("id", "sec-3");
        s3.put("type", "newsletter");
        s3.put("name", "Boletín");
        s3.put("visible", true);
        pageSections.add(s3);
    }

    public AdminApiController(PerfumeRepository perfumeRepository, FileService fileService) {
        this.perfumeRepository = perfumeRepository;
        this.fileService = fileService;
    }

    @GetMapping("/perfumes/{id}")
    public ResponseEntity<Perfume> getPerfume(@PathVariable String id) {
        return perfumeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/perfumes/upload")
    public ResponseEntity<Map<String, String>> uploadPerfumeImage(@RequestParam("file") MultipartFile file) {
        try {
            String url = fileService.uploadFile(file);
            Map<String, String> response = new HashMap<>();
            response.put("url", url);
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            Map<String, String> error = new HashMap<>();
            error.put("status", "error");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/perfumes/save")
    public ResponseEntity<Map<String, String>> savePerfume(@RequestBody Perfume perfume) {
        String action = (perfume.getId() == null || perfume.getId().isEmpty()) ? "CREAR" : "ACTUALIZAR";
        
        // Log auditing details
        Map<String, Object> log = new HashMap<>();
        log.put("timestamp", LocalDateTime.now());
        log.put("usuario", "admin@profumi.com");
        log.put("accion", action);
        log.put("producto", perfume.getNombre());
        log.put("detalles", "Se guardó el perfume: " + perfume.getNombre() + " (" + perfume.getMarca() + ")");
        auditLogs.add(0, log);

        perfumeRepository.save(perfume);
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("id", perfume.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/perfumes/duplicate/{id}")
    public ResponseEntity<Map<String, String>> duplicatePerfume(@PathVariable String id) {
        Perfume original = perfumeRepository.findById(id).orElse(null);
        if (original == null) {
            return ResponseEntity.notFound().build();
        }

        Perfume copy = new Perfume();
        copy.setNombre(original.getNombre() + " (Copia)");
        copy.setMarca(original.getMarca());
        copy.setDescripcion(original.getDescripcion());
        copy.setPrecio(original.getPrecio());
        copy.setImagenUrl(original.getImagenUrl());
        copy.setStock(original.getStock());
        copy.setSku(original.getSku() != null ? original.getSku() + "-COPY" : null);
        copy.setNotasOlfativas(original.getNotasOlfativas());
        copy.setTipoPielIdeal(original.getTipoPielIdeal());
        copy.setClimaRecomendado(original.getClimaRecomendado());
        copy.setOcasion(original.getOcasion());
        copy.setGenero(original.getGenero());
        copy.setDuracion(original.getDuracion());
        copy.setProyeccion(original.getProyeccion());
        
        // Copia de campos adicionales
        copy.setDescripcionCorta(original.getDescripcionCorta());
        copy.setDescripcionLarga(original.getDescripcionLarga());
        copy.setHistoria(original.getHistoria());
        copy.setPrecioAnterior(original.getPrecioAnterior());
        copy.setDescuento(original.getDescuento());
        copy.setSkuBarra(original.getSkuBarra());
        copy.setEstadoDisponibilidad(original.getEstadoDisponibilidad());
        copy.setCategorias(original.getCategorias());
        copy.setColecciones(original.getColecciones());
        copy.setEtiquetas(original.getEtiquetas());
        copy.setTipoFragancia(original.getTipoFragancia());
        copy.setFamiliaOlfativa(original.getFamiliaOlfativa());
        copy.setNotasSalida(original.getNotasSalida());
        copy.setNotasCorazon(original.getNotasCorazon());
        copy.setNotasFondo(original.getNotasFondo());
        copy.setConcentracion(original.getConcentracion());
        copy.setPaisOrigen(original.getPaisOrigen());
        copy.setAnoLanzamiento(original.getAnoLanzamiento());
        copy.setPerfumista(original.getPerfumista());
        copy.setTamanosDisponibles(original.getTamanosDisponibles());
        copy.setPeso(original.getPeso());
        copy.setDimensiones(original.getDimensiones());
        copy.setGarantia(original.getGarantia());
        copy.setPoliticaDevolucion(original.getPoliticaDevolucion());
        copy.setImagenesAdicionales(original.getImagenesAdicionales());
        copy.setVideos(original.getVideos());

        perfumeRepository.save(copy);

        // Audit Log
        Map<String, Object> log = new HashMap<>();
        log.put("timestamp", LocalDateTime.now());
        log.put("usuario", "admin@profumi.com");
        log.put("accion", "DUPLICAR");
        log.put("producto", copy.getNombre());
        log.put("detalles", "Se duplicó el perfume a partir de ID: " + id);
        auditLogs.add(0, log);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/perfumes/archive/{id}")
    public ResponseEntity<Map<String, String>> archivePerfume(@PathVariable String id) {
        Perfume perfume = perfumeRepository.findById(id).orElse(null);
        if (perfume == null) {
            return ResponseEntity.notFound().build();
        }

        perfume.setEstadoDisponibilidad("Archivado");
        perfumeRepository.save(perfume);

        // Audit Log
        Map<String, Object> log = new HashMap<>();
        log.put("timestamp", LocalDateTime.now());
        log.put("usuario", "admin@profumi.com");
        log.put("accion", "ARCHIVAR");
        log.put("producto", perfume.getNombre());
        log.put("detalles", "Se archivó el perfume.");
        auditLogs.add(0, log);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/perfumes/restore/{id}")
    public ResponseEntity<Map<String, String>> restorePerfume(@PathVariable String id) {
        Perfume perfume = perfumeRepository.findById(id).orElse(null);
        if (perfume == null) {
            return ResponseEntity.notFound().build();
        }

        perfume.setEstadoDisponibilidad("Disponible");
        perfumeRepository.save(perfume);

        // Audit Log
        Map<String, Object> log = new HashMap<>();
        log.put("timestamp", LocalDateTime.now());
        log.put("usuario", "admin@profumi.com");
        log.put("accion", "RESTAURAR");
        log.put("producto", perfume.getNombre());
        log.put("detalles", "Se restauró el perfume.");
        auditLogs.add(0, log);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/perfumes/patch/{id}")
    public ResponseEntity<Map<String, String>> patchPerfume(@PathVariable String id, @RequestBody Map<String, Object> payload) {
        Perfume perfume = perfumeRepository.findById(id).orElse(null);
        if (perfume == null) {
            return ResponseEntity.notFound().build();
        }

        String field = (String) payload.get("field");
        Object val = payload.get("value");

        String oldVal = "";
        if ("nombre".equals(field)) {
            oldVal = perfume.getNombre();
            perfume.setNombre((String) val);
        } else if ("precio".equals(field)) {
            oldVal = String.valueOf(perfume.getPrecio());
            perfume.setPrecio(Double.valueOf(String.valueOf(val)));
        } else if ("descripcion".equals(field)) {
            oldVal = perfume.getDescripcion();
            perfume.setDescripcion((String) val);
        } else if ("marca".equals(field)) {
            oldVal = perfume.getMarca();
            perfume.setMarca((String) val);
        } else if ("stock".equals(field)) {
            oldVal = String.valueOf(perfume.getStock());
            perfume.setStock(Integer.valueOf(String.valueOf(val)));
        }

        perfumeRepository.save(perfume);

        // Audit Log
        Map<String, Object> log = new HashMap<>();
        log.put("timestamp", LocalDateTime.now());
        log.put("usuario", "admin@profumi.com");
        log.put("accion", "MODIFICAR CAMPO: " + field.toUpperCase());
        log.put("producto", perfume.getNombre());
        log.put("detalles", "Cambio en: " + field + " de '" + oldVal + "' a '" + val + "'");
        auditLogs.add(0, log);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/perfumes/delete/{id}")
    public ResponseEntity<Map<String, String>> deletePerfume(@PathVariable String id) {
        Optional<Perfume> perfOpt = perfumeRepository.findById(id);
        if (perfOpt.isPresent()) {
            // Audit Log
            Map<String, Object> log = new HashMap<>();
            log.put("timestamp", LocalDateTime.now());
            log.put("usuario", "admin@profumi.com");
            log.put("accion", "ELIMINAR");
            log.put("producto", perfOpt.get().getNombre());
            log.put("detalles", "Se eliminó el producto permanentemente.");
            auditLogs.add(0, log);

            perfumeRepository.deleteById(id);
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/builder/sections")
    public ResponseEntity<List<Map<String, Object>>> getSections() {
        return ResponseEntity.ok(pageSections);
    }

    @PostMapping("/builder/save")
    public ResponseEntity<Map<String, String>> saveSections(@RequestBody List<Map<String, Object>> sections) {
        pageSections = sections;
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    public static List<Map<String, Object>> getAuditLogs() {
        return auditLogs;
    }
}
