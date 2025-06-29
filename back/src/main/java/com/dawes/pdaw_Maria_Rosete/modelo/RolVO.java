package com.dawes.pdaw_Maria_Rosete.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Clase que representa un rol de usuario.
 * 
 * @author Maria Rosete
 */
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
@Table(name="roles")
public class RolVO {
    
    /**
     * Identificador unico del rol.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idrol;
    
    /**
     * Nombre del rol.
     */
    @NonNull
    @Column(unique=true)
    @Enumerated(EnumType.STRING)
    private NombreRol nombre;
}

