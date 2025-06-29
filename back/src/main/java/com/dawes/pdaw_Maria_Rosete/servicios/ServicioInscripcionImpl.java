package com.dawes.pdaw_Maria_Rosete.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawes.pdaw_Maria_Rosete.modelo.CursoVO;
import com.dawes.pdaw_Maria_Rosete.modelo.InscripcionVO;
import com.dawes.pdaw_Maria_Rosete.modelo.UsuarioVO;
import com.dawes.pdaw_Maria_Rosete.repositorio.InscripcionRepository;

/**
 * Implementacion del servicio para la gestion de inscripciones.
 * 
 * @author Maria Rosete
 */
@Service
public class ServicioInscripcionImpl implements ServicioInscripcion {

    @Autowired
    private InscripcionRepository inscripcionRepository;
    
    @Autowired
    private ServicioUsuario servicioUsuario;

    /**
     * Guarda una inscripcion en la base de datos.
     * 
     * @param entity La inscripcion a guardar.
     * @return La inscripcion guardada.
     * 
     * 
     */
    @Override
    public <S extends InscripcionVO> S save(S entity) {
        return inscripcionRepository.save(entity);
    }

    /**
     * Obtiene todas las inscripciones almacenadas en la base de datos.
     * 
     * @return Lista de inscripciones.
     */
    @Override
    public List<InscripcionVO> findAll() {
        return inscripcionRepository.findAll();
    }

    /**
     * Busca una inscripcion por su ID.
     * 
     * @param id El ID de la inscripcion a buscar.
     * @return La inscripcion encontrada, o vacio si no se encuentra.
     */
    @Override
    public Optional<InscripcionVO> findById(Integer id) {
        return inscripcionRepository.findById(id);
    }

    /**
     * Elimina una inscripcion por su ID.
     * 
     * @param id El ID de la inscripcion a eliminar.
     */
    @Override
    public void deleteById(Integer id) {
        inscripcionRepository.deleteById(id);
    }

    /**
     * Busca una inscripcion por el usuario y el curso asociados.
     * 
     * @param usuario El usuario asociado a la inscripcion.
     * @param curso   El curso asociado a la inscripcion.
     * @return La inscripcion encontrada, o vacio si no se encuentra.
     */
    @Override
    public Optional<InscripcionVO> findByUsuarioAndCurso(UsuarioVO usuario, CursoVO curso) {
        return inscripcionRepository.findByUsuarioAndCurso(usuario, curso);
    }

    /**
     * Obtiene todas las inscripciones asociadas a un usuario.
     * 
     * @param usuarioVO El usuario del que se desean obtener las inscripciones.
     * @return Iterable de inscripciones del usuario.
     */
    @Override
    public Iterable<InscripcionVO> findByUsuario(UsuarioVO usuarioVO) {
        return inscripcionRepository.findByUsuario(usuarioVO);
    }

    /**
     * Elimina todas las inscripciones asociadas a un usuario.
     * 
     * @param idUsuario El ID del usuario del que se desean eliminar las inscripciones.
     */
    @Override
    public void eliminarInscripcionesUsuario(Integer idUsuario) {
        Optional<UsuarioVO> usuarioOptional = servicioUsuario.findById(idUsuario);
        if (usuarioOptional.isPresent()) {
            UsuarioVO usuario = usuarioOptional.get();
            Iterable<InscripcionVO> inscripciones = inscripcionRepository.findByUsuario(usuario);
            inscripcionRepository.deleteAll(inscripciones);
        }
    }
}

