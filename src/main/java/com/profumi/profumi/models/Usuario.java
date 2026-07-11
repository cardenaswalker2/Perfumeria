package com.profumi.profumi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;
    private String email;
    private String nombre;
    private String password;
    private Rol rol;
    private String proveedorAuth; // GOOGLE, LOCAL

    public Usuario() {}

    public Usuario(String email, String nombre, String password, Rol rol, String proveedorAuth) {
        this.email = email;
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
        this.proveedorAuth = proveedorAuth;
    }

    public Usuario(String email, String nombre, Rol rol, String proveedorAuth) {
        this.email = email;
        this.nombre = nombre;
        this.rol = rol;
        this.proveedorAuth = proveedorAuth;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
    public String getProveedorAuth() { return proveedorAuth; }
    public void setProveedorAuth(String proveedorAuth) { this.proveedorAuth = proveedorAuth; }
}
