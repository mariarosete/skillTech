package com.dawes.pdaw_Maria_Rosete.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dawes.pdaw_Maria_Rosete.modelo.NombreRol;
import com.dawes.pdaw_Maria_Rosete.modelo.RolVO;

/**
 * Repositorio para la entidad RolVO. Proporciona metodos para interactuar con la tabla de roles en la base de datos.
 * 
 * @author Maria Rosete
 */
public interface RolRepository extends CrudRepository<RolVO, Integer> {
    
    /**
     * Busca un rol por nombre.
     *
     * @param nombre El nombre del rol a buscar.
     * @return Un Optional que puede contener el rol si se encuentra, o vacio si no.
     */
    @Query("SELECT r FROM RolVO r WHERE r.nombre = :nombre")
    Optional<RolVO> findByNombre(NombreRol nombre);
}
