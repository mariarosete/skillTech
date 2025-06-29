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

import com.dawes.pdaw_Maria_Rosete.modelo.MaterialCursoVO;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioMaterialCurso;

/**
 * Controlador REST para gestionar los materiales de los cursos.
 * 
 * @author Maria Rosete
 */
@RestController
@RequestMapping("/materialCursos")
public class MaterialCursoWS {
    @Autowired
    private ServicioMaterialCurso sm;

    /**
     * Metodo para insertar un nuevo material de curso.
     * Solo los administradores pueden realizar esta operacion.
     * 
     * @param materialCurso El material de curso a insertar.
     * @return ResponseEntity con el resultado de la operacion.
     */
    @PostMapping("/insertar")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> insertar(@RequestBody MaterialCursoVO materialCurso) {
        try {
            MaterialCursoVO materiales = sm.save(materialCurso);
            return new ResponseEntity<>(materiales, HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Se ha producido un error al insertar un material " + ex.getCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Metodo para eliminar un material de curso.
     * Solo los administradores pueden realizar esta operacion.
     * 
     * @param idmaterialCurso El ID del material de curso a eliminar.
     * @return ResponseEntity con el resultado de la operacion.
     */
    @DeleteMapping("/eliminar/{idmaterialCurso}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> eliminar(@PathVariable Integer idmaterialCurso) {
        Map<String, Object> response = new HashMap<>();

        try {
            sm.deleteById(idmaterialCurso);
            response.put("message", "El material ha sido borrado");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al eliminar el material del curso: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Metodo para modificar un material de curso.
     * Solo los administradores pueden realizar esta operacion.
     * 
     * @param materialCurso El material de curso con los cambios.
     * @param idmaterialCurso El ID del material de curso a modificar.
     * @return ResponseEntity con el resultado de la operacion.
     */
    @PutMapping("/modificar/{idmaterialCurso}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> modificar(@RequestBody MaterialCursoVO materialCurso, @PathVariable Integer idmaterialCurso) {
        Optional<MaterialCursoVO> materialCursoToUpdate = sm.findById(idmaterialCurso);
        MaterialCursoVO updatedMaterialCurso;
        Map<String, Object> response = new HashMap<>();

        if (!materialCursoToUpdate.isPresent()) {
            response.put("message", "Error al modificar el material del curso: No se encontró el material a modificar");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            updatedMaterialCurso = materialCursoToUpdate.get();
            updatedMaterialCurso.setNombre(materialCurso.getNombre());
            updatedMaterialCurso.setTipoMaterial(materialCurso.getTipoMaterial());
            updatedMaterialCurso.setUrl(materialCurso.getUrl());

            updatedMaterialCurso = sm.save(updatedMaterialCurso);
        } catch (Exception ex) {
            response.put("message", "Error al modificar el material del curso: " + ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedMaterialCurso, HttpStatus.OK);
    }

    /**
     * Metodo para buscar todos los materiales de curso.
     * 
     * @return Iterable con todos los materiales de curso.
     */
    @GetMapping("/buscarMaterialCursos")
    @PreAuthorize("hasAnyAuthority('REGISTRADO', 'ADMINISTRADOR')")
    public Iterable listaMateriales() {
        return sm.findAll();
    }

    /**
     * Metodo para buscar un material de curso por su ID.
     * 
     * @param idmaterialCurso El ID del material de curso a buscar.
     * @return ResponseEntity con el resultado de la operacion.
     */
    @GetMapping("/buscarMaterialCursos/{idmaterialCurso}")
    @PreAuthorize("hasAnyAuthority('REGISTRADO', 'ADMINISTRADOR')")
    public ResponseEntity<?> findMaterial(@PathVariable Integer idmaterialCurso) {
        Optional<MaterialCursoVO> materialCurso = sm.findById(idmaterialCurso);
        if (materialCurso.isPresent()) {
            return new ResponseEntity<>(materialCurso.get(), HttpStatus.OK);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Material de curso no encontrado");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
