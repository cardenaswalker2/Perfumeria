package com.profumi.profumi.controllers;

import com.profumi.profumi.models.Perfume;
import com.profumi.profumi.repositories.PerfumeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    private final PerfumeRepository perfumeRepository;

    public CarritoController(PerfumeRepository perfumeRepository) {
        this.perfumeRepository = perfumeRepository;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Integer> getCarrito(HttpSession session) {
        Map<String, Integer> carrito = (Map<String, Integer>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new HashMap<>();
            session.setAttribute("carrito", carrito);
        }
        return carrito;
    }

    @GetMapping
    public String verCarrito(HttpSession session, Model model) {
        Map<String, Integer> carrito = getCarrito(session);
        Map<Perfume, Integer> items = new HashMap<>();
        double total = 0;

        for (Map.Entry<String, Integer> entry : carrito.entrySet()) {
            Perfume p = perfumeRepository.findById(entry.getKey()).orElse(null);
            if (p != null) {
                items.put(p, entry.getValue());
                if (p.getPrecio() != null) {
                    total += p.getPrecio() * entry.getValue();
                }
            }
        }

        model.addAttribute("items", items);
        model.addAttribute("total", total);
        model.addAttribute("title", "Tu Carrito | Profumi");
        return "carrito";
    }

    @PostMapping("/agregar/{id}")
    public org.springframework.http.ResponseEntity<?> agregarAlCarrito(
            @PathVariable String id, 
            @RequestParam(defaultValue = "1") int cantidad, 
            HttpSession session,
            @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
        
        Map<String, Integer> carrito = getCarrito(session);
        carrito.put(id, carrito.getOrDefault(id, 0) + cantidad);
        
        // Calcular total de items para el badge
        int totalItems = carrito.values().stream().mapToInt(Integer::intValue).sum();

        if ("XMLHttpRequest".equals(requestedWith)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("cartCount", totalItems);
            return org.springframework.http.ResponseEntity.ok(response);
        }

        return org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.FOUND)
                .location(java.net.URI.create("/carrito")).build();
    }

    @PostMapping("/actualizar/{id}")
    @ResponseBody
    public Object actualizarCantidad(
            @PathVariable String id, 
            @RequestParam int cantidad, 
            HttpSession session) {
        
        Map<String, Integer> carrito = getCarrito(session);
        if (cantidad > 0) {
            carrito.put(id, cantidad);
        } else {
            carrito.remove(id);
        }
        
        // Calcular totales
        int totalItems = carrito.values().stream().mapToInt(Integer::intValue).sum();
        double totalPrecio = 0;
        for (Map.Entry<String, Integer> entry : carrito.entrySet()) {
            Perfume p = perfumeRepository.findById(entry.getKey()).orElse(null);
            if (p != null && p.getPrecio() != null) totalPrecio += p.getPrecio() * entry.getValue();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("cartCount", totalItems);
        response.put("totalPrecio", totalPrecio);
        return response;
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarDelCarrito(@PathVariable String id, HttpSession session) {
        Map<String, Integer> carrito = getCarrito(session);
        carrito.remove(id);
        return "redirect:/carrito";
    }

    @GetMapping("/limpiar")
    public String limpiarCarrito(HttpSession session) {
        session.removeAttribute("carrito");
        return "redirect:/carrito";
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session) {
        Map<String, Integer> carrito = getCarrito(session);
        if (carrito.isEmpty()) return "redirect:/carrito";
        // Ahora redirigimos a la página de datos reales
        return "redirect:/carrito/pago";
    }

    @GetMapping("/pago")
    public String verPago(HttpSession session, Model model) {
        Map<String, Integer> carrito = getCarrito(session);
        if (carrito.isEmpty()) return "redirect:/catalogo";

        // Construir items y total para mostrar en el resumen del pedido
        Map<Perfume, Integer> items = new java.util.LinkedHashMap<>();
        double total = 0;
        for (Map.Entry<String, Integer> entry : carrito.entrySet()) {
            Perfume p = perfumeRepository.findById(entry.getKey()).orElse(null);
            if (p != null) {
                items.put(p, entry.getValue());
                if (p.getPrecio() != null) {
                    total += p.getPrecio() * entry.getValue();
                }
            }
        }

        model.addAttribute("items", items);
        model.addAttribute("total", total);
        model.addAttribute("title", "Finalizar Pedido | Profumi");
        return "pago";
    }

    @Autowired
    private com.profumi.profumi.repositories.PedidoRepository pedidoRepository;

    @PostMapping("/finalizar")
    public String finalizarPedido(
            @RequestParam String nombre,
            @RequestParam String direccion,
            @RequestParam String telefono,
            @RequestParam String metodoPago,
            HttpSession session, Model model) {
        
        Map<String, Integer> carrito = getCarrito(session);
        if (carrito.isEmpty()) return "redirect:/catalogo";

        double total = 0;
        for (Map.Entry<String, Integer> entry : carrito.entrySet()) {
            Perfume p = perfumeRepository.findById(entry.getKey()).orElse(null);
            if (p != null && p.getPrecio() != null) total += p.getPrecio() * entry.getValue();
        }

        // Guardar pedido en base de datos para el Asesor
        com.profumi.profumi.models.Pedido pedido = new com.profumi.profumi.models.Pedido();
        pedido.setNombreCliente(nombre);
        pedido.setDireccion(direccion);
        pedido.setTelefono(telefono);
        pedido.setMetodoPago(metodoPago);
        pedido.setItems(new java.util.HashMap<>(carrito));
        pedido.setTotal(total);
        pedidoRepository.save(pedido);

        StringBuilder sb = new StringBuilder("https://wa.me/573026636405?text="); 
        sb.append("¡Hola Profumi! Nuevo pedido confirmado (Ref: ").append(pedido.getId()).append("):\n\n");
        sb.append("*Datos del Cliente:*\n");
        sb.append("- Nombre: ").append(nombre).append("\n");
        sb.append("- Teléfono: ").append(telefono).append("\n");
        sb.append("- Dirección: ").append(direccion).append("\n");
        sb.append("- Método de Pago: ").append(metodoPago).append("\n\n");
        
        sb.append("*Productos:*\n");
        for (Map.Entry<String, Integer> entry : carrito.entrySet()) {
            Perfume p = perfumeRepository.findById(entry.getKey()).orElse(null);
            if (p != null) {
                sb.append("- ").append(entry.getValue()).append("x ").append(p.getNombre()).append(" ($")
                  .append(String.format("%,.0f", p.getPrecio() * entry.getValue())).append(")\n");
            }
        }
        sb.append("\n*Total: $").append(String.format("%,.0f", total)).append(" COP*");
        
        String whatsappUrl = sb.toString().replace(" ", "%20").replace("\n", "%0A");
        
        // Limpiamos carrito
        session.removeAttribute("carrito");
        
        model.addAttribute("whatsappUrl", whatsappUrl);
        model.addAttribute("pedido", pedido);
        model.addAttribute("title", "Pedido en Proceso | Profumi");
        return "agradecimiento";
    }
}
