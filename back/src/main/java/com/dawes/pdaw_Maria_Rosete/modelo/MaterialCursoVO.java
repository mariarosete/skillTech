package com.dawes.pdaw_Maria_Rosete.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Clase que representa un material asociado a un curso.
 * 
 * @author Maria Rosete
 */
@Entity
@Table(name = "materialcursos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class MaterialCursoVO {

    /**
     * Enumeracion que define los tipos de material disponibles.
     */
    public enum TipoMaterial {
        PDF(0),
        VIDEO(1),
        PRESENTACION(2),
        ENLACE(3),
        OTRO(4);

        private final int value;

        TipoMaterial(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * Identificador unico del material del curso.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idmaterialCurso;

    /**
     * Nombre del material.
     */
    @NonNull
    @Column(unique = true)
    private String nombre;

    /**
     * Tipo de material.
     */
    @NonNull
    @Enumerated(EnumType.ORDINAL)
    private TipoMaterial tipoMaterial;

    /**
     * URL del material.
     */
    @NonNull
    private String url;

    /**
     * Curso al que pertenece el material.
     */
    @NonNull
    @ManyToOne
    @JoinColumn(name = "curso_idcurso")
    private CursoVO curso;

    /**
     * Constructor para inicializar el nombre, tipo de material y URL del material.
     * @param nombre El nombre del material.
     * @param tipoMaterial El tipo de material.
     * @param url La URL del material.
     */
    public MaterialCursoVO(@NonNull String nombre, @NonNull TipoMaterial tipoMaterial, @NonNull String url) {
        this.nombre = nombre;
        this.tipoMaterial = tipoMaterial;
        this.url = url;
    }
}
