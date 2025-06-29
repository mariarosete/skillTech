package com.dawes.pdaw_Maria_Rosete.ws;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawes.pdaw_Maria_Rosete.modelo.CursoVO;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioCurso;

/**
 * Clase controladora para gestionar las peticiones relacionadas con los cursos.
 * 
 * @author Maria Rosete
 */
@RestController
@RequestMapping("/cursos")
public class CursoWS {
    @Autowired
    private ServicioCurso sc;
    
    /**
     * Endpoint para insertar un nuevo curso.
     * Solo los administradores pueden acceder a este endpoint.
     * 
     * @param curso El curso a insertar.
     * @return El curso insertado y un estado HTTP 200 si la operacion se realiza con exito, 
     *         o un mensaje de error y un estado HTTP 500 si ocurre algun problema.
     */
    @PostMapping("/insertar")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> insertar(@RequestBody CursoVO curso){
        try {
            CursoVO cur = sc.save(curso);
            return new ResponseEntity<CursoVO>(cur, HttpStatus.OK);
        } catch(Exception ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Se ha producido un error al insertar un curso: " + ex.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Endpoint para eliminar un curso por su ID.
     * Solo los administradores pueden acceder a este endpoint.
     * 
     * @param idcurso El ID del curso a eliminar.
     * @return Un mensaje de exito si el curso se elimina correctamente, o un mensaje de error si falla.
     */
    @DeleteMapping("/eliminar/{idcurso}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> eliminar(@PathVariable Integer idcurso){
        Map<String, Object> response = new HashMap<>();
        try {
            sc.deleteById(idcurso);
            response.put("message", "El curso ha sido borrado");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
        } catch(Exception e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>> (response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint para modificar un curso.
     * Solo los administradores pueden acceder a este endpoint.
     * 
     * @param curso El curso modificado.
     * @param idtitulo El ID del curso a modificar.
     * @return El curso modificado y un estado HTTP 200 si la operacion se realiza con exito, 
     *         o un mensaje de error y un estado HTTP 500 si ocurre algún problema.
     */
    @PutMapping("/modificar/{idtitulo}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> modificar (@RequestBody CursoVO curso, @PathVariable Integer idtitulo) {
        Optional<CursoVO> cursoToUpdate = sc.findById(idtitulo);
        CursoVO updatedCurso; 
        Map<String, Object> response = new HashMap<>();
        
        if(!cursoToUpdate.isPresent()) {
            response.put("message", "Error al modificar el curso: No se encontró el curso a modificar");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        
        try {
            updatedCurso = cursoToUpdate.get();
            updatedCurso.setDescripcion(curso.getDescripcion());
            updatedCurso.setCategoria(curso.getCategoria());
            updatedCurso.setTitulo(curso.getTitulo());
            
            updatedCurso = sc.save(updatedCurso);
        }
        catch(Exception ex) {
            response.put("message", "Error al modificar el curso: " + ex.getMessage());
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CursoVO>(updatedCurso, HttpStatus.OK);     
    }       

    /**
     * Endpoint para buscar todos los cursos.
     * Solo los usuarios registrados pueden acceder a este endpoint.
     * 
     * @return Una lista de todos los cursos y un estado HTTP 200 si la operacion se realiza con exito, 
     *         o un mensaje de error y un estado HTTP 500 si ocurre algun problema.
     */
    @GetMapping("/buscarCursos")
    @PreAuthorize("hasAnyAuthority('REGISTRADO', 'ADMINISTRADOR')")
    public Iterable listaCursos(){
        return sc.findAll();
    }
    
    /**
     * Endpoint para buscar un curso por su ID.
     * Solo los usuarios registrados pueden acceder a este endpoint.
     * 
     * @param idcurso El ID del curso a buscar.
     * @return El curso encontrado y un estado HTTP 200 si la operacion se realiza con exito, 
     *         o un mensaje de error y un estado HTTP 500 si ocurre algun problema.
     */
    @GetMapping("/buscarCursos/{idcurso}")
    @PreAuthorize("hasAnyAuthority('REGISTRADO', 'ADMINISTRADOR')")
    public Optional<CursoVO> findCurso(@PathVariable Integer idcurso) {
        return sc.findById(idcurso);
    }
    
    /**
     * Endpoint para buscar cursos por su titulo.
     * 
     * @param titulo El titulo del curso a buscar.
     * @return Una lista de cursos encontrados por titulo y un estado HTTP 200 si la operacion se realiza con exito, 
     *         o un mensaje de error y un estado HTTP 500 si ocurre algun problema.
     */
    @GetMapping("/buscarCursosPorTitulo/{titulo}")
    public ResponseEntity<?> buscarCursosPorTitulo(@PathVariable String titulo) {
        try {
            Iterable<CursoVO> cursos;
            
            if (titulo != null) {
                cursos = sc.findByTituloContainingIgnoreCase(titulo);
            } else {
                cursos = sc.findAll();
            }
            
            return new ResponseEntity<Iterable<CursoVO>>(cursos, HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error al buscar cursos por título: " + ex.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint para buscar cursos por su categoria.
     * 
     * @param categoria La categoria del curso a buscar.
     * @return Una lista de cursos encontrados por categoria y un estado HTTP 200 si la operacion se realiza con exito, 
     *         o un mensaje de error y un estado HTTP 500 si ocurre algun problema.
     */
    @GetMapping("/buscarCursosPorCategoria/{categoria}") 
    public ResponseEntity<?> buscarCursosPorCategoria(@PathVariable String categoria) {
        try {
            Iterable<CursoVO> cursos;
            
            if (categoria != null) {
                cursos = sc.findByCategoria(categoria);
            } else {
                cursos = sc.findAll();
            }
            
            return new ResponseEntity<Iterable<CursoVO>>(cursos, HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error al buscar cursos por categoría: " + ex.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
