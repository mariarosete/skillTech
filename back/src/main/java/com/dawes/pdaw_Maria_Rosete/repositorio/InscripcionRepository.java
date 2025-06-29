package com.dawes.pdaw_Maria_Rosete.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dawes.pdaw_Maria_Rosete.modelo.CursoVO;
import com.dawes.pdaw_Maria_Rosete.modelo.InscripcionVO;
import com.dawes.pdaw_Maria_Rosete.modelo.UsuarioVO;

import java.util.Optional;

/**
 * Repositorio para la entidad InscripcionVO. Proporciona metodos para interactuar con la tabla de inscripciones en la base de datos.
 * 
 * @author Maria Rosete
 */
@Repository
public interface InscripcionRepository extends JpaRepository<InscripcionVO, Integer> {
    
    /**
     * Busca una inscripcion por usuario y curso.
     *
     * @param usuario El usuario de la inscripcion a buscar.
     * @param curso   El curso de la inscripcion a buscar.
     * @return Un Optional que puede contener la inscripcion si se encuentra, o vacio si no.
     */
    Optional<InscripcionVO> findByUsuarioAndCurso(UsuarioVO usuario, CursoVO curso);

    /**
     * Busca las inscripciones de un usuario.
     *
     * @param usuario El usuario cuyas inscripciones se quieren buscar.
     * @return Una coleccion iterable de inscripciones del usuario.
     */
    Iterable<InscripcionVO> findByUsuario(UsuarioVO usuario);
}
