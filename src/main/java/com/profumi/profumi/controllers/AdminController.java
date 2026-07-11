package com.profumi.profumi.controllers;

import com.profumi.profumi.models.Perfume;
import com.profumi.profumi.models.Pedido;
import com.profumi.profumi.repositories.PerfumeRepository;
import com.profumi.profumi.repositories.PedidoRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final PerfumeRepository perfumeRepository;
    private final PedidoRepository pedidoRepository;
    private final com.profumi.profumi.services.FileService fileService;

    public AdminController(PerfumeRepository perfumeRepository, 
                           PedidoRepository pedidoRepository,
                           com.profumi.profumi.services.FileService fileService) {
        this.perfumeRepository = perfumeRepository;
        this.pedidoRepository = pedidoRepository;
        this.fileService = fileService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Perfume> perfumes = perfumeRepository.findAll();
        long totalProducts = perfumes.size();
        long lowStock = perfumes.stream().filter(p -> p.getStock() != null && p.getStock() < 5).count();
        
        List<Pedido> pedidos = pedidoRepository.findAll();
        double totalSales = pedidos.stream().mapToDouble(Pedido::getTotal).sum();
        long pendingOrders = pedidos.stream().filter(p -> "PENDIENTE".equals(p.getEstado())).count();

        // Advanced Stats
        double salesToday = totalSales * 0.12; // Simulated
        double conversionRate = 3.4; // Simulated %
        long abandonedCarts = 14; // Simulated count
        double averageTicket = totalSales / (pedidos.isEmpty() ? 1 : pedidos.size());

        // Simulated VIP Clients
        List<Map<String, Object>> clients = new ArrayList<>();
        Map<String, Object> c1 = new HashMap<>();
        c1.put("nombre", "Sofia Velez");
        c1.put("email", "sofia@example.com");
        c1.put("compras", 5);
        c1.put("total", 850.0);
        c1.put("vip", true);
        c1.put("notas", "Prefiere fragancias florales e intensas.");
        clients.add(c1);

        Map<String, Object> c2 = new HashMap<>();
        c2.put("nombre", "Mateo Gomez");
        c2.put("email", "mateo@example.com");
        c2.put("compras", 3);
        c2.put("total", 490.0);
        c2.put("vip", false);
        c2.put("notas", "Prefiere perfumes de noche y amaderados.");
        clients.add(c2);

        // Simulated Promotions
        List<Map<String, Object>> promos = new ArrayList<>();
        Map<String, Object> p1 = new HashMap<>();
        p1.put("codigo", "LUXURY20");
        p1.put("tipo", "Porcentaje (20%)");
        p1.put("limite", "Hasta 31 Jul");
        p1.put("estado", "Activo");
        promos.add(p1);

        Map<String, Object> p2 = new HashMap<>();
        p2.put("codigo", "BOGO2X1");
        p2.put("tipo", "2x1 (Colección Verano)");
        p2.put("limite", "Hasta 15 Jul");
        p2.put("estado", "Activo");
        promos.add(p2);

        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("lowStock", lowStock);
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("salesToday", salesToday);
        model.addAttribute("conversionRate", conversionRate);
        model.addAttribute("abandonedCarts", abandonedCarts);
        model.addAttribute("averageTicket", averageTicket);
        
        model.addAttribute("perfumes", perfumes);
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("clientes", clients);
        model.addAttribute("promociones", promos);
        model.addAttribute("auditLogs", AdminApiController.getAuditLogs());
        model.addAttribute("activePage", "dashboard");
        
        return "admin/dashboard";
    }

    @GetMapping("/perfumes")
    public String listPerfumes() {
        return "redirect:/admin/dashboard?tab=inventario";
    }

    @GetMapping("/perfumes/nuevo")
    public String nuevoPerfumeForm() {
        return "redirect:/admin/dashboard?tab=inventario&action=new";
    }

    @GetMapping("/perfumes/editar/{id}")
    public String editarPerfumeForm(@PathVariable String id) {
        return "redirect:/admin/dashboard?tab=inventario&action=edit&id=" + id;
    }

    @GetMapping("/perfumes/eliminar/{id}")
    public String eliminarPerfume(@PathVariable String id) {
        perfumeRepository.deleteById(id);
        return "redirect:/admin/dashboard?tab=inventario";
    }

    @PostMapping("/perfumes/guardar")
    public String guardarPerfume(@ModelAttribute Perfume perfume, 
                                 @RequestParam(value = "file", required = false) org.springframework.web.multipart.MultipartFile file) {
        try {
            if (perfume.getId() != null && !perfume.getId().isEmpty()) {
                Perfume existing = perfumeRepository.findById(perfume.getId()).orElse(null);
                if (existing != null && (file == null || file.isEmpty())) {
                    perfume.setImagenUrl(existing.getImagenUrl());
                }
            }

            if (file != null && !file.isEmpty()) {
                String url = fileService.uploadFile(file);
                perfume.setImagenUrl(url);
            }
            perfumeRepository.save(perfume);
        } catch (java.io.IOException e) {
            System.err.println("Error al subir archivo: " + e.getMessage());
        }
        return "redirect:/admin/dashboard?tab=inventario";
    }
}
