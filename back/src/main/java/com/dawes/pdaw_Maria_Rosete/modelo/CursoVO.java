package com.dawes.pdaw_Maria_Rosete.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
 * Clase que representa un curso.
 * 
 * @author Maria Rosete
 */
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
@Table(name="cursos")
public class CursoVO {
    
    /**
     * Identificador unico del curso.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true)
    private int idcurso;
    
    /**
     * Descripcion del curso.
     */
    @NonNull
    private String descripcion;
    
    /**
     * Categoria del curso.
     */
    @NonNull
    private String categoria;
    
    /**
     * Titulo del curso.
     */
    @NonNull
    private String titulo;
}
