package com.dawes.pdaw_Maria_Rosete.servicios;

import java.util.List;
import java.util.Optional;

import com.dawes.pdaw_Maria_Rosete.modelo.MaterialCursoVO;

/**
 * Interfaz que define los metodos de servicio para la gestion de materiales de cursos.
 * 
 * @author Maria Rosete
 */
public interface ServicioMaterialCurso {

    /**
     * Busca un material del curso por su nombre.
     * 
     * @param nombre El nombre del material del curso a buscar.
     * @return El material del curso encontrado, o vacio si no se encuentra.
     */
    Optional<MaterialCursoVO> findByNombre(String nombre);

    /**
     * Guarda un material del curso en la base de datos.
     * 
     * @param entity El material del curso a guardar.
     * @return El material del curso guardado.
     */
    <S extends MaterialCursoVO> S save(S entity);

    /**
     * Obtiene todos los materiales del curso almacenados en la base de datos.
     * 
     * @return Lista de materiales del curso.
     */
    List<MaterialCursoVO> findAll();

    /**
     * Busca un material del curso por su ID.
     * 
     * @param id El ID del material del curso a buscar.
     * @return El material del curso encontrado, o vacio si no se encuentra.
     */
    Optional<MaterialCursoVO> findById(Integer id);

    /**
     * Elimina un material del curso por su ID.
     * 
     * @param id El ID del material del curso a eliminar.
     */
    void deleteById(Integer id);
}
