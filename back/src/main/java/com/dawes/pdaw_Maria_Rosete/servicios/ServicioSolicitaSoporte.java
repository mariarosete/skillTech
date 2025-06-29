package com.dawes.pdaw_Maria_Rosete.servicios;

import java.util.List;
import java.util.Optional;

import com.dawes.pdaw_Maria_Rosete.modelo.SolicitaSoporteVO;
import com.dawes.pdaw_Maria_Rosete.modelo.UsuarioVO;

/**
 * Interfaz que define los metodos de servicio para la gestion de solicitar soporte.
 * 
 * @author Maria Rosete
 */
public interface ServicioSolicitaSoporte {
	
	/**
	 * Busca una solicitud de soporte por el ID del usuario y el ID del material del curso.
	 * 
	 * @param idusuario        El ID del usuario.
	 * @param idmaterial_curso El ID del material del curso.
	 * @return La solicitud de soporte encontrada, o vacio si no se encuentra.
	 */
	Optional<SolicitaSoporteVO> findByUsuarioAndMaterialCurso(int idusuario, int idmaterial_curso);
	
	/**
	 * Guarda una solicitud de soporte en la base de datos.
	 * 
	 * @param entity La solicitud de soporte a guardar.
	 * @return La solicitud de soporte guardada.
	 */
	<S extends SolicitaSoporteVO> S save(S entity);
	
	/**
	 * Obtiene todas las solicitudes de soporte almacenadas en la base de datos.
	 * 
	 * @return Una lista de todas las solicitudes de soporte.
	 */
	List<SolicitaSoporteVO> findAll();
	
	/**
	 * Busca una solicitud de soporte por su ID.
	 * 
	 * @param id El ID de la solicitud de soporte a buscar.
	 * @return La solicitud de soporte encontrada, o vacio si no se encuentra.
	 */
	Optional<SolicitaSoporteVO> findById(Integer id);
	
	/**
	 * Elimina una solicitud de soporte de la base de datos por su ID.
	 * 
	 * @param id El ID de la solicitud de soporte a eliminar.
	 */
	void deleteById(Integer id);
	
	/**
	 * Obtiene todas las solicitudes de soporte asociadas a un usuario.
	 * 
	 * @param usuario El usuario del que se desean obtener las solicitudes de soporte.
	 * @return Una coleccion iterable de todas las solicitudes de soporte del usuario.
	 */
	Iterable<SolicitaSoporteVO> findByUsuario(UsuarioVO usuario);		
}
