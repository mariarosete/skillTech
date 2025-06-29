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
 * Clase que representa una solicitud de soporte realizada por un usuario para un material de curso.
 * 
 * @author Maria Rosete
 */
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
@Table(name="solicitasoporte", uniqueConstraints = {@UniqueConstraint (columnNames = {"idusuario", "idmaterial_curso"})})
public class SolicitaSoporteVO {
    
    /**
     * Identificador unico de la solicitud de soporte.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idsolicitaSoporte;
    
    /**
     * Usuario que realiza la solicitud de soporte.
     */
    @NonNull
    @ManyToOne
    @JoinColumn(name="idusuario")
    private UsuarioVO usuario;
    
    /**
     * Material de curso al que se refiere la solicitud de soporte.
     */
    @NonNull
    @ManyToOne
    @JoinColumn(name="idmaterial_curso") 
    private MaterialCursoVO materialCurso;
    
    /**
     * Descripción de la solicitud de soporte.
     */
    @NonNull
    private String descripcion;
    
    /**
     * Respuesta a la solicitud de soporte.
     */
    @NonNull
    private String respuesta;
}
