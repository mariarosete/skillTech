package com.dawes.pdaw_Maria_Rosete.ws;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawes.pdaw_Maria_Rosete.modelo.CursoVO;
import com.dawes.pdaw_Maria_Rosete.modelo.InscripcionVO;
import com.dawes.pdaw_Maria_Rosete.modelo.UsuarioVO;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioCurso;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioInscripcion;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioUsuario;

/**
 * Controlador REST para gestionar las inscripciones de usuarios a cursos.
 * 
 * @author Maria Rosete
 */
@RestController
@RequestMapping("/inscripciones")
public class InscripcionWS {
    @Autowired
    private ServicioInscripcion si;
    
    @Autowired
    private ServicioUsuario su;
    
    @Autowired
    private ServicioCurso sc;
    
    /**
     * Metodo para insertar una nueva inscripcion de un usuario a un curso.
     * Solo los usuarios registrados pueden realizar esta operacion.
     * 
     * @param inscripcion La inscripcion a insertar.
     * @return ResponseEntity con el resultado de la operacion.
     */
    @PostMapping("/insertar")
    @PreAuthorize("hasAuthority('REGISTRADO')")
    public ResponseEntity<?> insertar(@RequestBody InscripcionVO inscripcion) {
        try {
            // Verificar si el usuario ya está inscrito en el curso
            ResponseEntity<?> respuesta = buscarInscripcionUsuarioCurso(inscripcion.getUsuario().getIdusuario(), inscripcion.getCurso().getIdcurso());
            if (respuesta.getStatusCode() == HttpStatus.OK) {
                // Si el usuario ya está inscrito, devolver un mensaje 
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Ya estás inscrito en este curso.");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }
            
            // Si el usuario no está inscrito, seguir con la inserción de la inscripción
            InscripcionVO insc = si.save(inscripcion);
            return new ResponseEntity<InscripcionVO>(insc, HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
           
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Ya estás inscrito en este curso.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error al procesar la inscripción.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Metodo para eliminar una inscripcion de usuario a un curso.
     * Solo los administradores pueden realizar esta operacion.
     * 
     * @param idinscripcion El ID de la inscripcion a eliminar.
     * @return ResponseEntity con el resultado de la operacion.
     */
    @DeleteMapping("/eliminar/{idinscripcion}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Integer idinscripcion) {
        Map<String, Object> response = new HashMap<>();

        try {
            si.deleteById(idinscripcion);
            response.put("message", "La inscripción ha sido borrada");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
        } catch(Exception e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>> (response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Metodo para buscar todas las inscripciones.
     * Solo los usuarios registrados pueden realizar esta operacion.
     * 
     * @return Iterable con todas las inscripciones.
     */
    @GetMapping("/buscarInscripciones")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'REGISTRADO')")
    public Iterable listaInscripciones(){
        return si.findAll();
    }
    
    /**
     * Metodo para buscar una inscripcion especifica de un usuario a un curso.
     * Solo los usuarios registrados pueden realizar esta operacion.
     * 
     * @param idusuario El ID del usuario.
     * @param idcurso El ID del curso.
     * @return ResponseEntity con el resultado de la operacion.
     */
    @GetMapping("/buscarInscripcionUsuarioCurso/{idusuario}/{idcurso}")
    @PreAuthorize("hasAuthority('REGISTRADO')")
    public ResponseEntity<?> buscarInscripcionUsuarioCurso(@PathVariable Integer idusuario, @PathVariable Integer idcurso) {
        try {
            Optional<UsuarioVO> usuario = su.findById(idusuario);
            Optional<CursoVO> curso = sc.findById(idcurso);
            
            if (usuario.isPresent() && curso.isPresent()) {
                Optional<InscripcionVO> inscripcion = si.findByUsuarioAndCurso(usuario.get(), curso.get());
                if (inscripcion.isPresent()) {
                    return ResponseEntity.ok("El usuario está inscrito en este curso.");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no está inscrito en este curso.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario o el curso no existe.");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener la inscripción del usuario en el curso.");
        }
    }
    
    /**
     * Metodo para buscar todas las inscripciones de un usuario.
     * Solo los usuarios registrados pueden realizar esta operacion.
     * 
     * @param idusuario El ID del usuario.
     * @return ResponseEntity con el resultado de la operacion.
     */
    @GetMapping("/buscarInscripcionesUsuario/{idusuario}")
    @PreAuthorize("hasAuthority('REGISTRADO')")
    public ResponseEntity<?> buscarInscripcionesUsuario(@PathVariable Integer idusuario) {
        try {
            Optional<UsuarioVO> usuario = su.findById(idusuario);
            if (usuario.isPresent()) {
                Iterable<InscripcionVO> inscripciones = si.findByUsuario(usuario.get());
                return new ResponseEntity<Iterable<InscripcionVO>>(inscripciones, HttpStatus.OK);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "El usuario no existe.");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error al obtener las inscripciones.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Metodo para eliminar todas las inscripciones de un usuario.
     * Solo los administradores pueden realizar esta operacion.
     * 
     * @param idusuario El ID del usuario.
     * @return ResponseEntity con el resultado de la operacion.
     */
    @DeleteMapping("/eliminarInscripcionesUsuario/{idusuario}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Map<String, Object>> eliminarInscripcionesUsuario(@PathVariable Integer idusuario) {
        Map<String, Object> response = new HashMap<>();

        try {
            
            si.eliminarInscripcionesUsuario(idusuario);
            response.put("message", "Las inscripciones del usuario han sido borradas");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
