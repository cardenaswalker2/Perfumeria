package com.profumi.profumi.controllers;

import com.profumi.profumi.models.Rol;
import com.profumi.profumi.models.Usuario;
import com.profumi.profumi.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String logout,
                            Model model) {
        model.addAttribute("title", "Iniciar Sesión | Profumi");
        if (error != null) {
            model.addAttribute("errorMsg", "Email o contraseña incorrectos. Intenta de nuevo.");
        }
        if (logout != null) {
            model.addAttribute("logoutMsg", "Has cerrado sesión exitosamente.");
        }
        return "login";
    }

    @GetMapping("/registro")
    public String registroPage(Model model) {
        model.addAttribute("title", "Crear Cuenta | Profumi");
        return "registro";
    }

    @PostMapping("/registro")
    public String registrar(@RequestParam String nombre,
                            @RequestParam String email,
                            @RequestParam String password,
                            @RequestParam String confirmPassword,
                            Model model) {

        // Validaciones
        if (nombre.isBlank() || email.isBlank() || password.isBlank()) {
            model.addAttribute("title", "Crear Cuenta | Profumi");
            model.addAttribute("errorMsg", "Todos los campos son obligatorios.");
            return "registro";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("title", "Crear Cuenta | Profumi");
            model.addAttribute("errorMsg", "Las contraseñas no coinciden.");
            return "registro";
        }

        if (password.length() < 6) {
            model.addAttribute("title", "Crear Cuenta | Profumi");
            model.addAttribute("errorMsg", "La contraseña debe tener al menos 6 caracteres.");
            return "registro";
        }

        Optional<Usuario> existente = usuarioRepository.findByEmail(email);
        if (existente.isPresent()) {
            model.addAttribute("title", "Crear Cuenta | Profumi");
            model.addAttribute("errorMsg", "Ya existe una cuenta con ese email.");
            return "registro";
        }

        // Crear usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setPassword(passwordEncoder.encode(password));
        nuevoUsuario.setRol(Rol.CLIENTE);
        nuevoUsuario.setProveedorAuth("LOCAL");
        usuarioRepository.save(nuevoUsuario);

        return "redirect:/login?registered=true";
    }

    @GetMapping("/recuperar-password")
    public String recuperarPasswordPage(Model model) {
        model.addAttribute("title", "Recuperar Contraseña | Profumi");
        return "recuperar-password";
    }

    @PostMapping("/recuperar-password")
    public String procesarRecuperarPassword(@RequestParam String email, Model model) {
        model.addAttribute("title", "Recuperar Contraseña | Profumi");
        // Simulate sending a recovery email in a luxurious manner
        model.addAttribute("successMsg", "Hemos enviado las instrucciones de recuperación a tu correo electrónico. Por favor, verifica tu bandeja de entrada en los próximos minutos.");
        return "recuperar-password";
    }
}

