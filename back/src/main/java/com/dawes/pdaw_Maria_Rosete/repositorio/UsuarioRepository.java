package com.dawes.pdaw_Maria_Rosete.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dawes.pdaw_Maria_Rosete.modelo.RolVO;
import com.dawes.pdaw_Maria_Rosete.modelo.UsuarioVO;

/**
 * Repositorio para la entidad UsuarioVO. Proporciona metodos para interactuar con la tabla de usuarios en la base de datos.
 * 
 * @author Maria Rosete
 */
public interface UsuarioRepository extends CrudRepository<UsuarioVO, Integer> {

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param nombre El nombre de usuario a buscar.
     * @return Un Optional que puede contener el usuario si se encuentra, o vacio si no.
     */
    @Query("SELECT u FROM UsuarioVO u LEFT JOIN FETCH u.rol r WHERE u.nombre = :nombre")
    Optional<UsuarioVO> buscarPorNombreUsuario(String nombre);
    
    /**
     * Busca un usuario por su ID.
     *
     * @param idusuario El ID del usuario a buscar.
     * @return Un Optional que puede contener el usuario si se encuentra, o vacio si no.
     */
    Optional<UsuarioVO> findById(Integer idusuario);
    
    /**
     * Busca un usuario por su correo electronico.
     *
     * @param correo El correo electronico del usuario a buscar.
     * @return El usuario encontrado.
     */
    UsuarioVO findByCorreo(String correo);
    
    /**
     * Busca una lista de usuarios por su rol.
     *
     * @param rol El rol de los usuarios a buscar.
     * @return Una lista de usuarios que tienen el rol especificado.
     */
    List<UsuarioVO> findByRol(RolVO rol);
}
