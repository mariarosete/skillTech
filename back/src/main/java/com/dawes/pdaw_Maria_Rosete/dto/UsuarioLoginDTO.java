package com.dawes.pdaw_Maria_Rosete.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Clase que representa un DTO (Data Transfer Object) para el inicio de sesion de un usuario.
 * 
 * @author Maria Rosete
 */
public class UsuarioLoginDTO {

    /**
     * El nombre de usuario para el inicio de sesion.
     */
    @NotNull
    @NotBlank
    private String nombreUsuario;

    /**
     * La contrasena para el inicio de sesion.
     */
    @NotNull
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String clave;

    /**
     * Obtiene el nombre de usuario.
     * @return El nombre de usuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Obtiene la contrasena.
     * @return La contrasena.
     */
    public String getClave() {
        return clave;
    }
}

