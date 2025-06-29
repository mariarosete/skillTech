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

import com.dawes.pdaw_Maria_Rosete.modelo.SolicitaSoporteVO;
import com.dawes.pdaw_Maria_Rosete.modelo.UsuarioVO;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioSolicitaSoporte;
import com.dawes.pdaw_Maria_Rosete.servicios.ServicioUsuario;

/**
 * Controlador REST para gestionar las solicitudes de soporte.
 * 
 * @author Maria Rosete
 */
@RestController
@RequestMapping("/solicitaSoporte")
public class SolicitaSoporteWS {
    
    @Autowired
    private ServicioSolicitaSoporte ssoporte;
    
    @Autowired
    private ServicioUsuario su;
    
    /**
     * Metodo para insertar una nueva solicitud de soporte.
     * 
     * @param solicitaSoporte La solicitud de soporte a insertar.
     * @return ResponseEntity con la solicitud de soporte insertada.
     */
    @PostMapping("/insertar")
    @PreAuthorize("hasAuthority('REGISTRADO')")
    public ResponseEntity<?> insertar(@RequestBody SolicitaSoporteVO solicitaSoporte){
        try {
            SolicitaSoporteVO soportes = ssoporte.save(solicitaSoporte);
            return new ResponseEntity<SolicitaSoporteVO>(soportes, HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Se ha producido un error al insertar un soporte " + ex.getCause());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Metodo para eliminar una solicitud de soporte por su ID.
     * 
     * @param idsolicitaSoporte El ID de la solicitud de soporte a eliminar.
     * @return ResponseEntity con el resultado de la operacion.
     */
    @DeleteMapping("/eliminar/{idsolicitaSoporte}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Integer idsolicitaSoporte) {
        Map<String, Object> response = new HashMap<>();

        try {
            ssoporte.deleteById(idsolicitaSoporte);
            response.put("message", "El material ha sido borrado");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>> (response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Metodo para modificar una solicitud de soporte.
     * 
     * @param solicitaSoporte La solicitud de soporte con los datos actualizados.
     * @param idSolicitud El ID de la solicitud de soporte a modificar.
     * @return ResponseEntity con la solicitud de soporte modificada.
     */
    @PutMapping("/modificar/{idSolicitud}")
    @PreAuthorize("hasAnyAuthority('REGISTRADO', 'ADMINISTRADOR')")
    public ResponseEntity<?> modificar(@RequestBody SolicitaSoporteVO solicitaSoporte,
                                         @PathVariable int idSolicitud) {
        Optional<SolicitaSoporteVO> solicitudToUpdate = ssoporte.findById(idSolicitud);
        SolicitaSoporteVO updatedSolicitaSoporte;
        Map<String, Object> response = new HashMap<>();

        if (!solicitudToUpdate.isPresent()) {
            response.put("message", "Error al modificar el formulario de solicitud de soporte: No se encontró el formulario de soporte");
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        try {
            updatedSolicitaSoporte = solicitudToUpdate.get();
            updatedSolicitaSoporte.setRespuesta(solicitaSoporte.getRespuesta());

            updatedSolicitaSoporte = ssoporte.save(updatedSolicitaSoporte);
        } catch (Exception ex) {
            response.put("message", "Error al modificar el formulario de solicitud de soporte: " + ex.getMessage());
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<SolicitaSoporteVO>(updatedSolicitaSoporte, HttpStatus.OK);        
    }
    
    /**
     * Metodo para obtener la lista de todas las solicitudes de soporte.
     * 
     * @return Iterable con la lista de todas las solicitudes de soporte.
     */
    @GetMapping("/buscarSoporte") 
    @PreAuthorize("hasAnyAuthority('REGISTRADO', 'ADMINISTRADOR')")
    public Iterable listaSoporte(){
        return ssoporte.findAll();
    }
    
    /**
     * Metodo para buscar una solicitud de soporte por el ID del usuario.
     * 
     * @param idusuario El ID del usuario.
     * @return ResponseEntity con la solicitud de soporte encontrada o un mensaje de error.
     */
    @GetMapping("/buscarSoporte/{idusuario}")
    @PreAuthorize("hasAnyAuthority('REGISTRADO', 'ADMINISTRADOR')")
    public ResponseEntity<?> buscarSoporte(@PathVariable Integer idusuario) {
        try {
            Optional<UsuarioVO> usuarioOptional = su.findById(idusuario);
            if (usuarioOptional.isPresent()) {
                UsuarioVO usuario = usuarioOptional.get();
                Iterable<SolicitaSoporteVO> soportes = ssoporte.findByUsuario(usuario);
                if (soportes.iterator().hasNext()) {
                    // Verificar si hay una respuesta del administrador
                    SolicitaSoporteVO soporte = soportes.iterator().next();
                    if (soporte.getRespuesta() != null && !soporte.getRespuesta().isEmpty()) {
                        return ResponseEntity.ok(soporte);
                    } else {
                        // Si no hay respuesta, devolver un mensaje
                        return ResponseEntity.ok("No hay respuesta disponible del administrador para esta solicitud de soporte.");
                    }
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "El usuario no existe.");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error al obtener el soporte: " + ex.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Metodo para eliminar todas las solicitudes de soporte de un usuario.
     * 
     * @param idusuario El ID del usuario.
     * @return ResponseEntity con el resultado de la operacion.
     */
    @DeleteMapping("/eliminarSolicitudesUsuario/{idusuario}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Map<String, Object>> eliminarSolicitudesUsuario(@PathVariable Integer idusuario) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<UsuarioVO> usuarioOptional = su.findById(idusuario);
            if (usuarioOptional.isPresent()) {
                UsuarioVO usuario = usuarioOptional.get();
                Iterable<SolicitaSoporteVO> solicitudes = ssoporte.findByUsuario(usuario);
                if (solicitudes.iterator().hasNext()) {
                    for (SolicitaSoporteVO solicitud : solicitudes) {
                        ssoporte.deleteById(solicitud.getIdsolicitaSoporte());
                    }
                    response.put("message", "Las solicitudes de soporte del usuario han sido borradas");
                }
                su.deleteById(idusuario);
                response.put("message", "El usuario y sus solicitudes de soporte han sido borrados correctamente");
            } else {
                response.put("message", "El usuario no existe");
            }
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

