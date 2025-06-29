package com.dawes.pdaw_Maria_Rosete.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dawes.pdaw_Maria_Rosete.modelo.SolicitaSoporteVO;
import com.dawes.pdaw_Maria_Rosete.modelo.UsuarioVO;

/**
 * Repositorio para la entidad SolicitaSoporteVO. Proporciona metodos para interactuar con la tabla de solicitudes de soporte en la base de datos.
 * 
 * @author Maria Rosete
 */
public interface SolicitaSoporteRepository extends JpaRepository<SolicitaSoporteVO, Integer> {
    
    /**
     * Busca una solicitud de soporte por ID de usuario e ID de material de curso.
     *
     * @param idusuario       El ID del usuario.
     * @param idmaterial_curso El ID del material de curso.
     * @return Un Optional que puede contener la solicitud de soporte si se encuentra, o vacio si no.
     */
    @Query("SELECT s FROM SolicitaSoporteVO s WHERE s.usuario.id = :idusuario AND s.materialCurso.id = :idmaterial_curso")
    Optional<SolicitaSoporteVO> findByUsuarioAndMaterialCurso(int idusuario, int idmaterial_curso);
    
    /**
     * Busca todas las solicitudes de soporte asociadas a un usuario.
     *
     * @param usuario El usuario del cual se quieren obtener las solicitudes de soporte.
     * @return Una coleccion iterable de solicitudes de soporte asociadas al usuario.
     */
    Iterable<SolicitaSoporteVO> findByUsuario(UsuarioVO usuario);
}
