package com.dawes.pdaw_Maria_Rosete.servicios;

import java.util.List;
import java.util.Optional;

import com.dawes.pdaw_Maria_Rosete.modelo.CursoVO;

/**
 * Interfaz que define los metodos de servicio para la gestion de cursos.
 * 
 * @author Maria Rosete
 */
public interface ServicioCurso {
    
    /**
     * Guarda un curso en la base de datos.
     *
     * @param entity El curso a guardar.
     * @return El curso guardado.
     */
    <S extends CursoVO> S save(S entity);
    
    /**
     * Obtiene todos los cursos.
     *
     * @return Una lista de todos los cursos.
     */
    List<CursoVO> findAll();
    
    /**
     * Busca un curso por su ID.
     *
     * @param id El ID del curso a buscar.
     * @return El curso encontrado, o vacio si no se encuentra.
     */
    Optional<CursoVO> findById(Integer id);
    
    /**
     * Elimina un curso por su ID.
     *
     * @param id El ID del curso a eliminar.
     */
    void deleteById(Integer id);

    /**
     * Busca cursos por titulo.
     *
     * @param titulo El titulo del curso a buscar.
     * @return Una lista de cursos que coinciden con el titulo especificado.
     */
    Iterable<CursoVO> findByTituloContainingIgnoreCase(String titulo);
    
    /**
     * Busca cursos por categoria.
     *
     * @param categoria La categoria del curso a buscar.
     * @return Una lista de cursos que pertenecen a la categoria especificada.
     */
    Iterable<CursoVO> findByCategoria(String categoria);
}
