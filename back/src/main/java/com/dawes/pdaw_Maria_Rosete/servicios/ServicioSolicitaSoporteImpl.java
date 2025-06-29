package com.dawes.pdaw_Maria_Rosete.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawes.pdaw_Maria_Rosete.modelo.SolicitaSoporteVO;
import com.dawes.pdaw_Maria_Rosete.modelo.UsuarioVO;
import com.dawes.pdaw_Maria_Rosete.repositorio.SolicitaSoporteRepository;

/**
 * Implementacion del servicio para la gestion de solicitar soporte
 * 
 * @author Maria Rosete
 */
@Service
public class ServicioSolicitaSoporteImpl implements ServicioSolicitaSoporte {
	
	@Autowired
	private SolicitaSoporteRepository ar;

	/**
	 * Guarda una solicitud de soporte en la base de datos.
	 * 
	 * @param entity La solicitud de soporte a guardar.
	 * @return La solicitud de soporte guardada.
	 */
	@Override
	public <S extends SolicitaSoporteVO> S save(S entity) {
		return ar.save(entity);
	}
	
	/**
	 * Obtiene todas las solicitudes de soporte almacenadas en la base de datos.
	 * 
	 * @return Una lista de todas las solicitudes de soporte.
	 */
	@Override
	public List<SolicitaSoporteVO> findAll() {
		return ar.findAll();
	}

	/**
	 * Busca una solicitud de soporte por su ID.
	 * 
	 * @param id El ID de la solicitud de soporte a buscar.
	 * @return La solicitud de soporte encontrada, o vacio si no se encuentra.
	 */
	@Override
	public Optional<SolicitaSoporteVO> findById(Integer id) {
		return ar.findById(id);
	}

	/**
	 * Elimina una solicitud de soporte de la base de datos por su ID.
	 * 
	 * @param id El ID de la solicitud de soporte a eliminar.
	 */
	@Override
	public void deleteById(Integer id) {
		ar.deleteById(id);
	}
	
	/**
	 * Busca una solicitud de soporte por el ID del usuario y el ID del material del curso.
	 * 
	 * @param idusuario        El ID del usuario.
	 * @param idmaterial_curso El ID del material del curso.
	 * @return La solicitud de soporte encontrada, o vacio si no se encuentra.
	 */
	@Override
	public Optional<SolicitaSoporteVO> findByUsuarioAndMaterialCurso(int idusuario, int idmaterial_curso) {
		return ar.findByUsuarioAndMaterialCurso(idusuario, idmaterial_curso);
	}

	/**
	 * Obtiene todas las solicitudes de soporte asociadas a un usuario.
	 * 
	 * @param usuario El usuario del que se desean obtener las solicitudes de soporte.
	 * @return Una coleccion iterable de todas las solicitudes de soporte del usuario.
	 */
	@Override
	public Iterable<SolicitaSoporteVO> findByUsuario(UsuarioVO usuario) {
		return ar.findByUsuario(usuario);
	}			
}
