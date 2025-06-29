package com.dawes.pdaw_Maria_Rosete.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawes.pdaw_Maria_Rosete.modelo.CursoVO;
import com.dawes.pdaw_Maria_Rosete.repositorio.CursoRepository;

/**
 * Implementacion del servicio para la gestion de cursos.
 * 
 * @author Maria Rosete
 */
@Service
public class ServicioCursoImpl implements ServicioCurso {

    @Autowired
    private CursoRepository ar;

    /**
     * Guarda un curso en la base de datos.
     *
     * @param entity El curso a guardar.
     * @return El curso guardado.
     */
    @Override
    public <S extends CursoVO> S save(S entity) {
        return ar.save(entity);
    }

    /**
     * Obtiene todos los cursos.
     *
     * @return Una lista de todos los cursos.
     */
    @Override
    public List<CursoVO> findAll() {
        return ar.findAll();
    }

    /**
     * Busca un curso por su ID.
     *
     * @param id El ID del curso a buscar.
     * @return El curso encontrado, o vacio si no se encuentra.
     */
    @Override
    public Optional<CursoVO> findById(Integer id) {
        return ar.findById(id);
    }

    /**
     * Elimina un curso por su ID.
     *
     * @param id El ID del curso a eliminar.
     */
    @Override
    public void deleteById(Integer id) {
        ar.deleteById(id);
    }

    /**
     * Busca cursos por titulo.
     *
     * @param titulo El titulo del curso a buscar.
     * @return Una lista de cursos que coinciden con el titulo especificado.
     */
    @Override
	public Iterable<CursoVO> findByTituloContainingIgnoreCase(String titulo) {
		// TODO Auto-generated method stub
		return ar.findByTituloContainingIgnoreCase(titulo);
	}

    /**
     * Busca cursos por categoria.
     *
     * @param categoria La categoria del curso a buscar.
     * @return Una lista de cursos que pertenecen a la categoria especificada.
     */
    @Override
    public Iterable<CursoVO> findByCategoria(String categoria) {
        return ar.findByCategoria(categoria);
    }

	
}

