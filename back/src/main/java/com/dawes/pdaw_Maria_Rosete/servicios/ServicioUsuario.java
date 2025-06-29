package com.dawes.pdaw_Maria_Rosete.servicios;

import java.util.List;

import java.util.Optional;

import com.dawes.pdaw_Maria_Rosete.dto.UsuarioDTO;
import com.dawes.pdaw_Maria_Rosete.dto.UsuarioLoginDTO;
import com.dawes.pdaw_Maria_Rosete.modelo.RolVO;
import com.dawes.pdaw_Maria_Rosete.modelo.UsuarioVO;

/**
 * Interfaz que define los metodos de servicio para la gestion de usuarios.
 * 
 * @author Maria Rosete
 */
public interface ServicioUsuario {
	
	/**
	 * Realiza el proceso de inicio de sesion para un usuario.
	 * 
	 * @param usuarioLoginDTO Los datos de inicio de sesion del usuario.
	 * @return Un objeto UsuarioDTO con la informacion del usuario y su token de autenticacion.
	 */
	UsuarioDTO login(UsuarioLoginDTO usuarioLoginDTO);
	
	/**
	 * Crea un nuevo usuario en el sistema.
	 * 
	 * @param usuarioDTO Los datos del usuario a crear.
	 * @return Un objeto UsuarioDTO con la informacion del usuario creado.
	 * @throws Exception Si ocurre un error durante el proceso de creacion del usuario.
	 */
	UsuarioDTO crear(UsuarioDTO usuarioDTO) throws Exception;

		
	/**
	 * Busca un usuario por su nombre de usuario.
	 * 
	 * @param nombre El nombre de usuario a buscar.
	 * @return Un objeto Optional que puede contener al usuario encontrado o estar vacio.
	 */
	Optional<UsuarioVO> buscarPorNombreUsuario(String nombre);
	
	/**
	 * Elimina un usuario por su ID.
	 * 
	 * @param id El ID del usuario a eliminar.
	 */
	void deleteById(Integer id);
	
	/**
	 * Busca un usuario por su ID.
	 * 
	 * @param id El ID del usuario a buscar.
	 * @return Un objeto Optional que puede contener al usuario encontrado o estar vacio.
	 */
	Optional<UsuarioVO> findById(Integer id);
	
	/**
	 * Obtiene todos los usuarios almacenados en el sistema.
	 * 
	 * @return Una coleccion iterable de todos los usuarios.
	 */
	Iterable<UsuarioVO> findAll();
	
	/**
	 * Busca un usuario por su correo electronico.
	 * 
	 * @param correo El correo electronico del usuario a buscar.
	 * @return El usuario encontrado, o null si no se encuentra.
	 */
	UsuarioVO findByCorreo(String correo);
	
	/**
	 * Busca todos los usuarios que tienen un rol especifico.
	 * 
	 * @param rol El rol por el que se desea buscar usuarios.
	 * @return Una lista de usuarios que tienen el rol especificado.
	 */
	List<UsuarioVO> findByRol(RolVO rol);
	
	/**
	 * Guarda los cambios realizados en un usuario en la base de datos.
	 * 
	 * @param updatedUsuario El usuario con los cambios que se desean guardar.
	 * @return El usuario actualizado.
	 */
	UsuarioVO save(UsuarioVO updatedUsuario);
}
