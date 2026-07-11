package com.profumi.profumi.controllers;

import com.profumi.profumi.models.Pedido;
import com.profumi.profumi.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.profumi.profumi.models.Usuario;
import com.profumi.profumi.models.Rol;
import com.profumi.profumi.repositories.PerfumeRepository;
import com.profumi.profumi.repositories.UsuarioRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/asesor")
public class AsesorController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PerfumeRepository perfumeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Pedido> pedidosPendientes = pedidoRepository.findByEstado("PENDIENTE");
        List<Pedido> pedidosConfirmados = pedidoRepository.findByEstado("CONFIRMADO");
        
        // Clientes VIP
        List<Usuario> clientesVip = usuarioRepository.findAll().stream()
            .filter(u -> u.getRol() == Rol.CLIENTE)
            .collect(Collectors.toList());

        // Alertas de Stock
        List<com.profumi.profumi.models.Perfume> lowStockPerfumes = perfumeRepository.findAll().stream()
            .filter(p -> p.getStock() != null && p.getStock() < 5)
            .collect(Collectors.toList());

        // Mapa de perfumes para resolver los items del pedido
        Map<String, com.profumi.profumi.models.Perfume> perfumeMap = perfumeRepository.findAll().stream()
            .collect(Collectors.toMap(com.profumi.profumi.models.Perfume::getId, p -> p));

        model.addAttribute("pendientes", pedidosPendientes);
        model.addAttribute("confirmados", pedidosConfirmados);
        model.addAttribute("clientesVip", clientesVip);
        model.addAttribute("lowStockPerfumes", lowStockPerfumes);
        model.addAttribute("perfumeMap", perfumeMap);
        model.addAttribute("title", "Panel de Asesor Experto | Profumi");
        return "asesor/dashboard";
    }

    @PostMapping("/confirmar/{id}")
    public String confirmarPedido(@PathVariable String id) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido != null) {
            pedido.setEstado("CONFIRMADO");
            pedidoRepository.save(pedido);
        }
        return "redirect:/asesor/dashboard";
    }

    @PostMapping("/cancelar/{id}")
    public String cancelarPedido(@PathVariable String id) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido != null) {
            pedido.setEstado("CANCELADO");
            pedidoRepository.save(pedido);
        }
        return "redirect:/asesor/dashboard";
    }
}
