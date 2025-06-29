package com.dawes.pdaw_Maria_Rosete.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dawes.pdaw_Maria_Rosete.modelo.MaterialCursoVO;

import java.util.Optional;

/**
 * Repositorio para la entidad MaterialCursoVO. Proporciona metodos para interactuar con la tabla de materiales de cursos en la base de datos.
 * 
 * @author Maria Rosete
 */
@Repository
public interface MaterialCursoRepository extends JpaRepository<MaterialCursoVO, Integer> {
    
    /**
     * Busca un material de curso por nombre.
     *
     * @param nombre El nombre del material de curso a buscar.
     * @return Un Optional que puede contener el material de curso si se encuentra, o vacio si no.
     */
    @Query("SELECT m FROM MaterialCursoVO m WHERE m.nombre = :nombre")
    Optional<MaterialCursoVO> findByNombre(String nombre);
}
