package com.dawes.pdaw_Maria_Rosete.servicios;

import java.util.List;
import java.util.Optional;

import com.dawes.pdaw_Maria_Rosete.modelo.CursoVO;
import com.dawes.pdaw_Maria_Rosete.modelo.InscripcionVO;
import com.dawes.pdaw_Maria_Rosete.modelo.UsuarioVO;

/**
 * Interfaz que define los metodos para el servicio de inscripciones.
 * 
 * @author Maria Rosete
 */
public interface ServicioInscripcion {

    /**
     * Busca una inscripcion por usuario y curso.
     *
     * @param usuario El usuario asociado a la inscripcion.
     * @param curso   El curso asociado a la inscripcion.
     * @return La inscripcion encontrada, o vacia si no se encuentra.
     */
    Optional<InscripcionVO> findByUsuarioAndCurso(UsuarioVO usuario, CursoVO curso);

    /**
     * Guarda una inscripcion en la base de datos.
     *
     * @param entity La inscripcion a guardar.
     * @return La inscripcion guardada.
     */
    <S extends InscripcionVO> S save(S entity);

    /**
     * Obtiene todas las inscripciones.
     *
     * @return Una lista de todas las inscripciones.
     */
    List<InscripcionVO> findAll();

    /**
     * Busca una inscripcion por su ID.
     *
     * @param id El ID de la inscripcion a buscar.
     * @return La inscripcion encontrada, o vacia si no se encuentra.
     */
    Optional<InscripcionVO> findById(Integer id);

    /**
     * Elimina una inscripcion por su ID.
     *
     * @param id El ID de la inscripcion a eliminar.
     */
    void deleteById(Integer id);

    /**
     * Busca todas las inscripciones asociadas a un usuario.
     *
     * @param usuario El usuario asociado a las inscripciones.
     * @return Una lista de inscripciones asociadas al usuario.
     */
    Iterable<InscripcionVO> findByUsuario(UsuarioVO usuario);

    /**
     * Elimina todas las inscripciones asociadas a un usuario.
     *
     * @param idUsuario El ID del usuario cuyas inscripciones se eliminaran.
     */
    void eliminarInscripcionesUsuario(Integer idUsuario);
}
