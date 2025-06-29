package com.dawes.pdaw_Maria_Rosete.servicios;

import java.util.List;
import java.util.Optional;

import com.dawes.pdaw_Maria_Rosete.modelo.NombreRol;
import com.dawes.pdaw_Maria_Rosete.modelo.RolVO;

/**
 * Interfaz que define los metodos de servicio para la gestion de roles.
 * 
 * @author Maria Rosete
 */
public interface ServicioRol {
	
	/**
	 * Busca un rol por su nombre.
	 * 
	 * @param nombre El nombre del rol a buscar.
	 * @return El rol encontrado, o vacio si no se encuentra.
	 */
	Optional<RolVO> findByNombreRol(NombreRol nombre);
	
	/**
	 * Guarda un rol en la base de datos.
	 * 
	 * @param rol El rol a guardar.
	 * @return El rol guardado.
	 */
	RolVO guardarRol(RolVO rol);
	
	/**
	 * Actualiza un rol en la base de datos.
	 * 
	 * @param id  El ID del rol a actualizar.
	 * @param rol El nuevo rol con los datos actualizados.
	 * @return El rol actualizado.
	 */
	RolVO actualizarRol(Integer id, RolVO rol);
	
	/**
	 * Elimina un rol de la base de datos por su ID.
	 * 
	 * @param id El ID del rol a eliminar.
	 */
	void eliminarRol(Integer id);
	
	/**
	 * Obtiene un rol por su ID.
	 * 
	 * @param id El ID del rol a obtener.
	 * @return El rol encontrado.
	 */
	RolVO obtenerRolPorId(Integer id);
	
	/**
	 * Obtiene todos los roles almacenados en la base de datos.
	 * 
	 * @return Una coleccion iterable de todos los roles.
	 */
	Iterable<RolVO> obtenerTodosLosRoles();
	
	/**
	 * Guarda todos los roles dados en la base de datos.
	 * 
	 * @param roles La lista de roles a guardar.
	 * @return Una coleccion iterable de los roles guardados.
	 */
	Iterable<RolVO> saveAll(List<RolVO> roles);
}
