package com.dawes.pdaw_Maria_Rosete.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Clase que representa una inscripcion a un curso por parte de un usuario.
 * 
 * @author Maria Rosete
 */
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
@Table(name="inscripciones", uniqueConstraints = {@UniqueConstraint (columnNames = {"idusuario","idcurso"})})
public class InscripcionVO {
    
    /**
     * Identificador unico de la inscripcion.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idinscripcion;
    
    /**
     * Usuario que realiza la inscripcion.
     */
    @NonNull
    @ManyToOne
    @JoinColumn(name="idusuario")
    private UsuarioVO usuario;
    
    /**
     * Curso al que se inscribe el usuario.
     */
    @NonNull
    @ManyToOne
    @JoinColumn(name="idcurso")
    private CursoVO curso;
}

