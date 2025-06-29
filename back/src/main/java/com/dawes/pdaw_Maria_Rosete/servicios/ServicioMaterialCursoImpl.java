package com.dawes.pdaw_Maria_Rosete.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawes.pdaw_Maria_Rosete.modelo.MaterialCursoVO;
import com.dawes.pdaw_Maria_Rosete.repositorio.MaterialCursoRepository;

/**
 * Implementacion del servicio para la gestion de materiales de los cursos.
 * 
 * @author Maria Rosete
 */
@Service
public class ServicioMaterialCursoImpl implements ServicioMaterialCurso {
	
	@Autowired
	private MaterialCursoRepository ar;

	/**
	 * Guarda un material del curso en la base de datos.
	 * 
	 * @param entity El material del curso a guardar.
	 * @return El material del curso guardado.
	 */
	@Override
	public <S extends MaterialCursoVO> S save(S entity) {
		return ar.save(entity);
	}

	/**
	 * Obtiene todos los materiales del curso almacenados en la base de datos.
	 * 
	 * @return Lista de materiales del curso.
	 */
	@Override
	public List<MaterialCursoVO> findAll() {
		return ar.findAll();
	}

	/**
	 * Busca un material del curso por su ID.
	 * 
	 * @param id El ID del material del curso a buscar.
	 * @return El material del curso encontrado, o vacio si no se encuentra.
	 */
	@Override
	public Optional<MaterialCursoVO> findById(Integer id) {
		return ar.findById(id);
	}

	/**
	 * Elimina un material del curso por su ID.
	 * 
	 * @param id El ID del material del curso a eliminar.
	 */
	@Override
	public void deleteById(Integer id) {
		ar.deleteById(id);
	}

	/**
	 * Busca un material del curso por su nombre.
	 * 
	 * @param nombre El nombre del material del curso a buscar.
	 * @return El material del curso encontrado, o vacio si no se encuentra.
	 */
	@Override
	public Optional<MaterialCursoVO> findByNombre(String nombre) {
		return ar.findByNombre(nombre);
	}
}
